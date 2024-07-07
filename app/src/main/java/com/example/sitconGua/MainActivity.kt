package com.example.sitconGua

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sitconGua.ui.theme.ButtonModifier
import com.example.sitconGua.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MyApplicationTheme {
                var showSecondScreen by remember { mutableStateOf(false) }
                var latitude by remember { mutableStateOf(0.0) }
                var longitude by remember { mutableStateOf(0.0) }

                if (showSecondScreen) {
                    SecondScreen(latitude, longitude)
                } else {
                    MainScreen(
                        onNavigate = {
                            // 取得裝置現在的經緯度
                            getLastKnownLocation { lat, lon ->
                                latitude = lat
                                longitude = lon
                                showSecondScreen = true
                            }
                        }
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(callback: (Double, Double) -> Unit) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        callback(it.latitude, it.longitude)
                    }
                }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    @Composable
    fun MainScreen(onNavigate: () -> Unit) {
        Column(
            modifier = ButtonModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigateButton(onNavigate)
        }
    }

    @Composable
    fun NavigateButton(onNavigate: () -> Unit) {
        Button(onClick = onNavigate) {
            Text("AED Map")
        }
    }

    @Composable
    fun SecondScreen(latitude: Double, longitude: Double) {
        val context = LocalContext.current
        Log.d("test", "test1")
        var urlsAndInfos by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
        var isLoading by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            val response = fetchAEDData(latitude, longitude)
            if (response.isNotEmpty()) {
                urlsAndInfos = response
                isLoading = false
                // Logging the URLs
                Log.d("AED URL", "URL1: ${urlsAndInfos.getOrNull(0)?.first}")
                Log.d("AED URL", "URL2: ${urlsAndInfos.getOrNull(1)?.first}")
                Log.d("AED URL", "URL3: ${urlsAndInfos.getOrNull(2)?.first}")
            }
        }

        if (isLoading) {
            Text("Loading...")
        } else {
            Column(
                modifier = ButtonModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                urlsAndInfos.forEachIndexed { index, (url, info) ->
                    Text(text = info, modifier = Modifier.clickable {
                        openUrl(url)
                    })
                    if (index < urlsAndInfos.size - 1) {
                        Text(text = "\n") // Add spacing between items
                    }
                }
            }
        }
    }


    private suspend fun fetchAEDData(latitude: Double, longitude: Double): List<Pair<String, String>> = withContext(Dispatchers.IO) {
        val url = URL("http://192.168.169.159:8088/api/v1/dashboard/get-nearby-AED?lat=$latitude&lng=$longitude&limit=3")
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("AED Response", response) // 打印返回的 JSON 字符串
                val json = JSONObject(response)
                val data = json.getJSONArray("data")
                return@withContext (0 until data.length()).map { i ->
                    val cnt = i+1
                    val result = data.getJSONObject(i)
                    val mapUrl = result.getString("MapUrl")
                    val name = result.getString("name")
                    val address = result.getString("address")
                    val place = result.getString("place")
                    Pair(mapUrl, "$cnt. $name\n\t\t $address\n\t\t $place")
                }
            }
        } finally {
            connection.disconnect()
        }
        emptyList()
    }

}

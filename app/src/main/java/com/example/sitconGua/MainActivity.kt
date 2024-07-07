package com.example.sitconGua

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sitconGua.components.MainScreen
import com.example.sitconGua.components.SecondScreen
import com.example.sitconGua.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MyApplicationTheme(darkTheme = true) {
                var showSecondScreen by remember { mutableStateOf(false) }
                var latitude by remember { mutableDoubleStateOf(0.0) }
                var longitude by remember { mutableDoubleStateOf(0.0) }

                if (showSecondScreen) {
                    SecondScreen(latitude.toString(), longitude.toString(), this)
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

}

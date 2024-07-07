package com.example.sitconGua.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sitconGua.models.AEDList
import com.example.sitconGua.ui.theme.ScreenModifier
import com.example.sitconGua.utils.httpClient

@Composable
fun SecondScreen(latitude: String, longitude: String, activity: ComponentActivity) {
    val context = LocalContext.current
    Log.d("test", "test1")
    val client = httpClient
    var urlsAndInfos by remember { mutableStateOf<AEDList>(AEDList(emptyList(), "")) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val response = client.getAEDList(mapOf("lat" to latitude, "lng" to longitude, "limit" to "5"))
        try {
            urlsAndInfos = response
            isLoading = false
            // Logging the URLs
            urlsAndInfos.data.forEachIndexed() { index, info ->
                Log.d("AED URL", "URL${index+1}: ${info.mapUrl}")
            }
        } catch (e: Exception) {
            urlsAndInfos = AEDList(emptyList(), "")
        }
    }

    if (isLoading) {
        Text("Loading...")
    } else {
        Column(
            modifier = ScreenModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            urlsAndInfos.data.forEach{ info ->
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = info.name, modifier = Modifier.padding(5.dp).clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(info.mapUrl))
                        activity.startActivity(intent)
                    })
                    System.err.println(info.place)
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = info.place, modifier = Modifier.padding(5.dp), fontSize = 12.sp)
                        Text(text = String.format("distance: %.3fkm", info.distance), modifier = Modifier.padding(5.dp), fontSize = 12.sp)
                    }
                }
            }

        }
    }
}
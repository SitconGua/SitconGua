package com.example.sitconGua.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService

@Composable
fun CurrentLocationScr() {
    val context = LocalContext.current
    var locationText by remember { mutableStateOf("Location not available") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { updateLocation(context) { location ->
            locationText = "Lat: ${location.latitude}, Lon: ${location.longitude}"
        } }) {
            Text("Get Current Location")
        }
        Text(locationText)
    }
}

@SuppressLint("MissingPermission")
private fun updateLocation(context: Context, onLocationUpdated: (Location) -> Unit) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    if (isGpsEnabled || isNetworkEnabled) {
        val locationProvider = if (isGpsEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER
        val location = locationManager.getLastKnownLocation(locationProvider)
        location?.let {
            onLocationUpdated(it)
        } ?: run {
            // 處理位置為空的情況
        }
    }
}

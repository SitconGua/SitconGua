package com.example.sitconGua

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import com.example.sitconGua.button.AED
import com.example.sitconGua.ui.theme.ButtonModifier
import com.example.sitconGua.ui.theme.MyApplicationTheme
import com.example.sitconGua.utils.CurrentLocationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val url="https://www.google.com/maps/search/?api=1&query=25.033459,121.501280"
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Column(modifier = ButtonModifier) {

                    AED { openUrl(url) }

                    CurrentLocationScreen()
                }
            }
        }
    }
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}


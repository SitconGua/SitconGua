package com.example.sitconGua.button

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AED(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "AED")
    }
}
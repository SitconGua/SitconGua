package com.example.sitconGua.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sitconGua.ui.theme.ScreenModifier

@Composable
fun MainScreen(onNavigate: () -> Unit) {
    Scaffold(
        bottomBar = { NavigateButton(onNavigate) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("welcome to Sitcon Gua", modifier = Modifier.padding(innerPadding).padding(12.dp))
        }
    }
}

@Composable
fun NavigateButton(onNavigate: () -> Unit) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.absolutePadding(bottom = 48.dp, left = 155.dp)) {
        IconButton(onClick = onNavigate) {
            Icon(Icons.Filled.Place, "Back")
        }

    }
}



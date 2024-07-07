package com.example.sitconGua.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sitconGua.ui.theme.ScreenModifier

@Composable
fun MainScreen(onNavigate: () -> Unit) {
    Scaffold(
        bottomBar = { NavigateButton(onNavigate) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("welcome to Sitcon Gua", modifier = Modifier.padding(innerPadding).padding(12.dp), fontSize = 30.sp)
        }
    }
}

@Composable
fun NavigateButton(onNavigate: () -> Unit) {
    Box {
        BottomAppBar(modifier = Modifier.align(Alignment.Center).height(70.dp)) { }
        IconButton(onClick = onNavigate, modifier = Modifier.padding(bottom = 20.dp).clip(CircleShape).align(Alignment.Center).background(Color.LightGray)) {
            Icon(Icons.Filled.Place, "AED Map", tint = Color.DarkGray)

        }
    }



}



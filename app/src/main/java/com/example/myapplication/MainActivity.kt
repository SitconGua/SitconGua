package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val url="https://www.google.com/maps/search/?api=1&query=25.033459,121.501280"
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column (modifier = Modifier.padding(innerPadding),){
                        Button(onClick = {openUrl(url)}){Text("AED")}
                    }

                }
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onTextClick: (String) -> Unit) {
    Text(
        text = "Hello $name!",
        modifier = modifier.clickable {
            onTextClick("https://www.google.com/maps/search/?api=1&query=25.033459,121.501280") // 指定你要跳轉的網址
        }
    )
}

@Composable
fun NearestAED(url:String ,onClick: (String) -> Unit) {
    Button(onClick = {onClick(url)}) {
        Text("AED"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting(
            name = "Android",
            onTextClick = { url -> /* 在預覽中不需要處理點擊事件 */ }
        )
    }
}

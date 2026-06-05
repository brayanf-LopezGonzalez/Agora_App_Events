package com.example.agora_app_events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.agora_app_events.ui.screens.HomeScreen
import com.example.agora_app_events.ui.theme.AgoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgoraTheme {
                HomeScreen()
            }
        }
    }
}
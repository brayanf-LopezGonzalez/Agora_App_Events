package com.example.agora_app_events.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AgoraColorScheme = lightColorScheme(
    primary = AgoraOrange,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = TextPrimary,
    surface = Color.White,
    onSurface = TextPrimary,
    secondary = StatusActive,
    onSecondary = Color.White,
    error = BtnDanger,
    onError = Color.White,
)

@Composable
fun AgoraTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AgoraColorScheme,
        typography = Typography,
        content = content
    )
}
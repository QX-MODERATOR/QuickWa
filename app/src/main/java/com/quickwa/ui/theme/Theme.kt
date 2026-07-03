package com.quickwa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = GreenTertiary,
    background = Color(0xFFF7FBF9),
    surface = Color.White,
)

private val DarkColors = darkColorScheme(
    primary = GreenSecondary,
    secondary = GreenTertiary,
    tertiary = GreenSecondary,
    background = Color(0xFF0B1411),
    surface = Color(0xFF0F1C18),
)

@Composable
fun QuickWhatsAppMessageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}


package com.uttkarsh.InstaStudio.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    primaryContainer = mainBeach,
    onPrimaryContainer = white,
    onPrimary = black,
    surfaceContainerLowest = cardLightest,
    surfaceContainerLow = cardMain,
    surfaceContainerHigh = cardDark,
    surfaceContainer = cardMedium,
    onSurfaceVariant = cardLight,
    secondaryContainer = secondaryBackground,
    tertiaryContainer = tertiaryBackground,
    error = errorRed,
    errorContainer = mainRed,
    tertiary = mainGreen

)

@Composable
fun InstaStudioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
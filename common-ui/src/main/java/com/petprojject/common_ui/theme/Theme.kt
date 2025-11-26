package com.petprojject.common_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

data class AppCustomColors(
    val warning: Color,
    val success: Color,
    val danger: Color,
)

val LightCustomColors = AppCustomColors(
    warning = Color(0xFFFFA000),
    success = Color(0xFF4CAF50),
    danger = Color(0xFFD32F2F),
)
val LocalCustomColors = staticCompositionLocalOf<AppCustomColors> {
    error("Custom Colors not provided")
}

@Composable
fun AutoPetProjectLakhaiTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCustomColors provides LightCustomColors
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}

object AppTheme {
    val customColors: AppCustomColors
        @Composable
        get() = LocalCustomColors.current
}
package com.petprojject.common_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
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
    val backgroundColor: Color,
    val tryAgainButtonContainer: Color,
    val textColor: Color,
    val descriptionColor: Color,
    val iconColor: Color,
    val lightIconColor: Color,
    val cardBorderColor: Color,
    val resultCardBackground: Color,
    val choiceCardBackground: Color,
    val mainButtonBackground: Color,
    val deleteRedColor: Color
)

val LightCustomColors = AppCustomColors(
    backgroundColor = Color(0xFFB7B2C5),
    tryAgainButtonContainer = Color(0xFF486C72),
    textColor = Color(0xFF000000),
    descriptionColor = Color(0xFF2F2F2F),
    iconColor = Color(0xFF000000),
    cardBorderColor = Color(0xFF888888),
    resultCardBackground = Color(0xFFD3C6E3),
    choiceCardBackground = Color(0xFF808080),
    mainButtonBackground = Color(0x4DCCBFBF),
    deleteRedColor = Color(0xFF9B0000),
    lightIconColor = Color(0xFFAFAFAF),
)

val DarkCustomColors = AppCustomColors(
    backgroundColor = Color(0xFF19171D),
    tryAgainButtonContainer = Color(0xFF5A858C),
    textColor = Color(0xFFE6E1E5),
    descriptionColor = Color(0xFFC8C4CC),
    iconColor = Color(0xFFE6E1E5),
    lightIconColor = Color(0xFF605D66),
    cardBorderColor = Color(0xFF49454F),
    resultCardBackground = Color(0xFF3F384C),
    choiceCardBackground = Color(0xFF3E3E3E),
    mainButtonBackground = Color(0x4D5C5454),
    deleteRedColor = Color(0xFFCF6679)
)
val LocalCustomColors = staticCompositionLocalOf<AppCustomColors> {
    error("Custom Colors not provided")
}

@Composable
fun AutoPetProjectLakhaiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appCustomColors = if (darkTheme) DarkCustomColors else LightCustomColors
    CompositionLocalProvider(
        LocalCustomColors provides appCustomColors
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}

object CarTheme {
    val customColors: AppCustomColors
        @Composable
        get() = LocalCustomColors.current
}
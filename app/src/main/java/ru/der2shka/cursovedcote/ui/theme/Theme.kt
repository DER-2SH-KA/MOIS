package ru.der2shka.cursovedcote.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

val font_size_main_text = TextUnit(32f, TextUnitType.Sp)
val font_size_secondary_text = TextUnit(24f, TextUnitType.Sp)

val line_height_main_text = font_size_main_text.value * 1.2f
val line_height_secondary_text = font_size_secondary_text.value * 1.2f

private val DarkColorScheme = darkColorScheme(
    primary = VeryLightBlue,
    secondary = SoftCyanLighter,
    tertiary = LightOrange,
    error = VividRed,
    background = VeryDarkGrayMostlyBlack
)

private val LightColorScheme = lightColorScheme(
    primary = SoftBlue,
    secondary = SoftCyan,
    tertiary = VividOrange,
    error = StrongRed,
    background = VeryLightGrayMostlyWhite

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

@Composable
fun CursovedCotETheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package edu.uvg.ian.tictactoe.ui.theme



import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = BlueDark,
    secondary = RedDark,
    background = Color.Black,
    surface = Color.DarkGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorPalette = lightColorScheme(
    primary = BlueLight,
    secondary = RedLight,
    background = BackgroundColor,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextColorPrimary,
    onSurface = TextColorSecondary
)

@Composable
fun TicTacToeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,

        content = content
    )
}

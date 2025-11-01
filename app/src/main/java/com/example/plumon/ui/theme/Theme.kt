package com.example.plumon.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
// Importaciones de tus colores personalizados (Deben existir en Color.kt)
import com.example.plumon.ui.theme.DeepPurple
import com.example.plumon.ui.theme.Purple40
import com.example.plumon.ui.theme.Purple80
import com.example.plumon.ui.theme.PurpleGrey40
import com.example.plumon.ui.theme.PurpleGrey80
import com.example.plumon.ui.theme.Pink40
import com.example.plumon.ui.theme.Pink80


// --- DEFINE LOS ESQUEMAS DE COLOR (Light y Dark) ---
// NOTA: 'DeepPurple' se usa aquí como color de fondo.

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    // Fondo Morado en tema oscuro
    background = DeepPurple,
    surface = DeepPurple // Superficies (como Cards)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    // Fondo Morado en tema claro
    background = DeepPurple,
    surface = DeepPurple // Superficies (como Cards)
    /* Otras opciones por defecto comentadas */
)

@Composable
fun PlumonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Para forzar tus colores morados, puedes poner dynamicColor = false
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Lógica para Android 12+ (colores dinámicos)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Temas Light y Dark definidos por ti
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Aplica el color primario a la barra de estado
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Asumo que tienes una variable/objeto Typography definido
        content = content
    )
}
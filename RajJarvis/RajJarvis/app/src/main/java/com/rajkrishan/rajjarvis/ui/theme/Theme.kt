package com.rajkrishan.rajjarvis.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val JarvisColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = VoidBlack,
    secondary = NeonPurple,
    onSecondary = TextPrimary,
    tertiary = NeonPink,
    background = DeepSpace,
    onBackground = TextPrimary,
    surface = PanelDark,
    onSurface = TextPrimary,
    surfaceVariant = PanelDarkAlt,
    onSurfaceVariant = TextSecondary,
    error = StatusDanger,
    outline = GlassBorder
)

@Composable
fun RajJarvisTheme(
    // Dark mode only, per spec — parameter kept for future-proofing only.
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = JarvisColorScheme,
        typography = RajJarvisTypography,
        content = content
    )
}

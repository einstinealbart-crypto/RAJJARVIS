package com.rajkrishan.rajjarvis.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan

/** Text with a soft neon halo — used for the JARVIS wordmark and section titles. */
@Composable
fun GlowText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = NeonCyan,
    style: TextStyle = LocalTextStyle.current,
    glowRadius: Float = 18f
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = style.copy(
            shadow = Shadow(
                color = color.copy(alpha = 0.85f),
                blurRadius = glowRadius
            )
        )
    )
}

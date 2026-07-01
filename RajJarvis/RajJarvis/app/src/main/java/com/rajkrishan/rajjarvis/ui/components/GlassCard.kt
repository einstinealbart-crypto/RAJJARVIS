package com.rajkrishan.rajjarvis.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rajkrishan.rajjarvis.ui.theme.GlassFill
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.NeonPurple
import com.rajkrishan.rajjarvis.ui.theme.PanelDark
import com.rajkrishan.rajjarvis.ui.theme.PanelDarkAlt

/**
 * Frosted "HUD panel" surface: translucent fill, subtle gradient border
 * that gently pulses, rounded corners. Used everywhere in place of a
 * plain Card so the whole app reads as one holographic interface.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 20,
    borderGlow: Boolean = true,
    content: @Composable () -> Unit
) {
    val infinite = rememberInfiniteTransition(label = "glow")
    val glow by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    val shape = RoundedCornerShape(cornerRadius.dp)
    val borderAlpha = if (borderGlow) glow else 0.4f

    Box(
        modifier = modifier
            .clip(shape)
            .background(Brush.verticalGradient(listOf(PanelDark, PanelDarkAlt)))
            .background(GlassFill)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        NeonCyan.copy(alpha = borderAlpha),
                        NeonPurple.copy(alpha = borderAlpha * 0.7f),
                        Color.Transparent
                    )
                ),
                shape = shape
            )
            .padding(16.dp)
    ) {
        content()
    }
}

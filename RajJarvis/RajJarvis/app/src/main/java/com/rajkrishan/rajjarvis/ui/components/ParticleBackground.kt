package com.rajkrishan.rajjarvis.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.rajkrishan.rajjarvis.ui.theme.DeepSpace
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.NeonPurple
import com.rajkrishan.rajjarvis.ui.theme.VoidBlack
import kotlin.math.sin
import kotlin.random.Random

private data class Particle(
    val x: Float,       // 0..1 normalized
    val y: Float,       // 0..1 normalized
    val radius: Float,
    val speed: Float,
    val phase: Float
)

/**
 * Full-screen animated backdrop: deep gradient + drifting neon particles
 * + a few faint "circuit" lines. Cheap to run (single Canvas, no bitmaps)
 * so it stays smooth even on mid-range devices.
 */
@Composable
fun ParticleBackground(
    modifier: Modifier = Modifier,
    particleCount: Int = 46
) {
    val particles = remember {
        List(particleCount) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 2.2f + 0.6f,
                speed = Random.nextFloat() * 0.6f + 0.2f,
                phase = Random.nextFloat() * 6.28f
            )
        }
    }

    val infinite = rememberInfiniteTransition(label = "bg")
    val t by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1_000_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(VoidBlack, DeepSpace))
            )
    ) {
        val w = size.width
        val h = size.height

        // Faint circuit lines
        val lineAlpha = 0.06f
        drawCircuitLines(w, h, lineAlpha)

        // Drifting particles with soft glow
        particles.forEach { p ->
            val drift = sin(t * p.speed + p.phase)
            val cx = p.x * w
            val cy = ((p.y * h) + drift * 18f).mod(h)
            val glowColor = if (p.phase.toInt() % 2 == 0) NeonCyan else NeonPurple

            drawCircle(
                color = glowColor.copy(alpha = 0.10f),
                radius = p.radius * 6f,
                center = Offset(cx, cy)
            )
            drawCircle(
                color = glowColor.copy(alpha = 0.85f),
                radius = p.radius,
                center = Offset(cx, cy)
            )
        }
    }
}

private fun DrawScope.drawCircuitLines(w: Float, h: Float, alpha: Float) {
    val rows = 5
    for (i in 0 until rows) {
        val y = h * (i + 0.5f) / rows
        drawLine(
            color = NeonCyan.copy(alpha = alpha),
            start = Offset(0f, y),
            end = Offset(w, y),
            strokeWidth = 1f
        )
    }
    val cols = 4
    for (i in 0 until cols) {
        val x = w * (i + 0.5f) / cols
        drawLine(
            color = NeonPurple.copy(alpha = alpha),
            start = Offset(x, 0f),
            end = Offset(x, h),
            strokeWidth = 1f
        )
    }
}

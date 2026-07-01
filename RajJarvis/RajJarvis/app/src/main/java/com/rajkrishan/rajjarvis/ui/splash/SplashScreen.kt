package com.rajkrishan.rajjarvis.ui.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajkrishan.rajjarvis.ui.components.GlowText
import com.rajkrishan.rajjarvis.ui.components.ParticleBackground
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.NeonPurple
import com.rajkrishan.rajjarvis.ui.theme.PanelDarkAlt
import com.rajkrishan.rajjarvis.ui.theme.TextMuted
import kotlinx.coroutines.delay

private val BOOT_LINES = listOf(
    "INITIALIZING CORE SYSTEMS…",
    "CALIBRATING NEURAL LINK…",
    "LOADING VOICE MATRIX…",
    "SYNCING DEVICE SENSORS…",
    "WELCOME BACK, RAJ."
)

/**
 * Animated splash / boot screen. Calls [onFinished] once the boot sequence
 * completes so the caller can navigate to Home. "Welcome back, Raj" is
 * shown as the final boot line here — wire it to TextToSpeech in a later
 * round for the actual voice greeting.
 */
@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var progress by remember { mutableIntStateOf(0) }
    var lineIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (progress < 100) {
            delay(35)
            progress += 2
            lineIndex = (progress * BOOT_LINES.size / 100).coerceAtMost(BOOT_LINES.size - 1)
        }
        delay(400)
        onFinished()
    }

    val infinite = rememberInfiniteTransition(label = "ring")
    val ringRotation by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "rotation"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize(), particleCount = 70)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(140.dp)) {
                    rotate(ringRotation) {
                        drawArc(
                            color = NeonCyan,
                            startAngle = 0f,
                            sweepAngle = 260f,
                            useCenter = false,
                            style = Stroke(width = 4f)
                        )
                    }
                    rotate(-ringRotation * 1.4f) {
                        drawArc(
                            color = NeonPurple,
                            startAngle = 90f,
                            sweepAngle = 140f,
                            useCenter = false,
                            style = Stroke(width = 3f)
                        )
                    }
                }
                GlowText(
                    text = "RJ",
                    color = NeonCyan,
                    style = MaterialTheme.typography.displayLarge
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            GlowText(
                text = "RAJ JARVIS",
                color = NeonCyan,
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 34.sp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Made by Rajkrishan",
                color = TextMuted,
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = BOOT_LINES[lineIndex],
                color = NeonCyan.copy(alpha = 0.85f),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoadingBar(progress = progress)

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$progress%",
                color = TextMuted,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun LoadingBar(progress: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(6.dp)
            .clip(RoundedCornerShape(50))
            .background(PanelDarkAlt)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress / 100f)
                .height(6.dp)
                .clip(RoundedCornerShape(50))
                .background(NeonCyan)
        )
    }
}

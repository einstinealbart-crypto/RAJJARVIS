package com.rajkrishan.rajjarvis.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajkrishan.rajjarvis.ui.theme.NeonBlue
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.NeonPurple
import com.rajkrishan.rajjarvis.ui.theme.VoidBlack

/** Pill-shaped neon-gradient action button. */
@Composable
fun NeonButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Brush.horizontalGradient(listOf(NeonBlue, NeonCyan, NeonPurple)))
            .clickable(
                interactionSource = remember(),
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = VoidBlack,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            letterSpacing = 1.sp
        )
    }
}

/** Circular glowing icon button, used for the floating voice orb / mic actions. */
@Composable
fun NeonIconButton(
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Int = 56,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Brush.radialGradient(listOf(NeonCyan, NeonPurple)))
            .border(1.dp, NeonCyan.copy(alpha = 0.6f), CircleShape)
            .clickable(
                interactionSource = remember(),
                indication = null,
                onClick = onClick
            )
            .padding((size / 4).dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = VoidBlack
        )
    }
}

@Composable
private fun remember(): MutableInteractionSource =
    androidx.compose.runtime.remember { MutableInteractionSource() }

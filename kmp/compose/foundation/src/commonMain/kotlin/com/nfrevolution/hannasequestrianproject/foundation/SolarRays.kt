package com.nfrevolution.hannasequestrianproject.foundation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
public fun SolarRays(
    modifier: Modifier = Modifier,
    color: Color
) {
    val infiniteTransition = rememberInfiniteTransition()
    val ray1Pulsation by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val ray2Pulsation by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val ray3Pulsation by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 2.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2
        val rayCount = 16
        val innerRadius = radius * 0.85f

        for (i in 0 until rayCount) {
            val angle = (i * 360f / rayCount) * (PI / 180f)
            val pulsation = when (i % 3) {
                0 -> ray1Pulsation
                1 -> ray2Pulsation
                else -> ray3Pulsation
            }
            val outerRadius = innerRadius + (radius * 0.5f * pulsation)

            val startX = centerX + innerRadius * cos(angle).toFloat()
            val startY = centerY + innerRadius * sin(angle).toFloat()
            val endX = centerX + outerRadius * cos(angle).toFloat()
            val endY = centerY + outerRadius * sin(angle).toFloat()

            for (layer in 0..20) {
                val layerWidth = 2f + (layer * 1.5f)
                val layerAlpha = 0.15f / (1 + layer * 0.3f)

                drawLine(
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    brush = Brush.linearGradient(
                        colors = listOf(
                            color.copy(alpha = layerAlpha * 1.5f),
                            color.copy(alpha = layerAlpha * 0.8f),
                            color.copy(alpha = layerAlpha * 0.3f),
                            Color.Transparent
                        ),
                        start = Offset(startX, startY),
                        end = Offset(endX, endY)
                    ),
                    strokeWidth = layerWidth
                )
            }
        }
    }
}

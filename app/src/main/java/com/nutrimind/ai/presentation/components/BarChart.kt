package com.nutrimind.ai.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

@Composable
fun BarChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    maxRange: Float = (data.maxOrNull() ?: 1f) * 1.2f
) {
    val barColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    Canvas(modifier = modifier.height(200.dp).fillMaxWidth()) {
        val barWidth = size.width / (data.size * 2)
        val spaceBetween = barWidth
        
        data.forEachIndexed { index, value ->
            val left = index * (barWidth + spaceBetween)
            val top = size.height - (value / maxRange * size.height)
            
            // Draw background track
            drawRoundRect(
                color = trackColor,
                topLeft = Offset(left, 0f),
                size = Size(barWidth, size.height),
                cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
            )
            
            // Draw progress bar
            drawRoundRect(
                color = barColor,
                topLeft = Offset(left, top),
                size = Size(barWidth, size.height - top),
                cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
            )
        }
    }
}

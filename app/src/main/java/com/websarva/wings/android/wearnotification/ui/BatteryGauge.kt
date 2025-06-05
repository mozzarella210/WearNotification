package com.websarva.wings.android.wearnotification.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BatteryGauge(batteryLevel: Int) {
    val batteryColor = when {
        batteryLevel > 50 -> Color(0xFF4CAF50)
        batteryLevel in 21..50 -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
    }

    val animatedLevel by animateFloatAsState(
        targetValue = batteryLevel.toFloat(),
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(100.dp)) {
            drawArc(
                color = batteryColor,
                startAngle = -90f,
                sweepAngle = (360f * animatedLevel / 100f),
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "🔋\n${animatedLevel.toInt()}%",
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
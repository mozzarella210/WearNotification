package com.websarva.wings.android.wearnotification.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.websarva.wings.android.wearnotification.ui.theme.*

@Composable
fun NixieBatteryGauge(batteryLevel: Int, flickerAlpha: Float) {

    val animatedLevel by animateFloatAsState(
        targetValue = batteryLevel.toFloat(),
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier
            .size(130.dp)
            .background(nixieDarkBackground, shape = RoundedCornerShape(16.dp))
            .border(2.dp, nixieLightOrange, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(110.dp)) {
            drawArc(
                color = nixieOrange,
                startAngle = -90f,
                sweepAngle = (360f * animatedLevel / 100f),
                useCenter = false,
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "🔋${animatedLevel.toInt()}%",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.alpha(flickerAlpha),
            color = nixieOrange,
            textAlign = TextAlign.Center
        )
    }
}
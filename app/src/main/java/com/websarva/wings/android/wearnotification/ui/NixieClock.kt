package com.websarva.wings.android.wearnotification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.websarva.wings.android.wearnotification.R
import com.websarva.wings.android.wearnotification.ui.theme.*
import kotlin.text.forEach

@Composable
fun NixieClock(
    currentTime: String,
    flickerAlpha : Float
) {
    val orbitronFont = FontFamily(
        Font(R.font.orbitron_medium, weight = FontWeight.Medium)
    )

    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .background(Color.Black),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        currentTime.forEach { digit ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp, 56.dp)
                    .border(2.dp, nixieLightOrange, RoundedCornerShape(8.dp))
                    .background(nixieBackground, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = digit.toString(),
                    fontFamily = orbitronFont,
                    fontSize = 24.sp,
                    color = nixieOrange,
                    modifier = Modifier.alpha(flickerAlpha),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        shadow = Shadow(
                            color = nixieOrange,
                            offset = Offset(0f, 0f),
                            blurRadius = 10f
                        )
                    )
                )
            }
        }
    }
}

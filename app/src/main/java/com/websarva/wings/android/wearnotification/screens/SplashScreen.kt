package com.websarva.wings.android.wearnotification.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.random.Random
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.Color as GColor

@Composable
fun SplashScreen(navController: NavController) {

    /*
    val user = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        delay(1000)
        if (user != null) {
            navController.navigate(Screens.HOME.name) {
                popUpTo(Screens.SPLASH.name) { inclusive = true }
            }
        } else {
            navController.navigate(Screens.LOGIN.name) {
                popUpTo(Screens.SPLASH.name) { inclusive = true }
            }

        }
    }

     */

    /*
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "🚀 起動中...",
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }

     */
    /*
    val infiniteTransition = rememberInfiniteTransition()
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F2027),
                        Color(0xFF203A43),
                        Color(0xFF2C5364)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🚀 起動中...",
                fontSize = 28.sp,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = alphaAnim),
                    shadow = Shadow(
                        color = Color(0xFFFFA726),
                        offset = Offset(0f, 0f),
                        blurRadius = 10f
                    )
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                strokeWidth = 4.dp
            )
        }
    }

     */

    /*
    var noiseBitmap by remember { mutableStateOf(generateNoise()) }

    // ちょっとずつノイズ更新
    LaunchedEffect(Unit) {
        while (true) {
            noiseBitmap = generateNoise()
            kotlinx.coroutines.delay(100) // 0.1秒ごとに更新（速さ調整）
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // ノイズ描画
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawNoise(noiseBitmap)
        }

        // テキスト浮かせる
        Text(
            text = "🚀 起動中...",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(16.dp)
        )
    }

     */

    val width = 300
    val height = 300
    var bitmap by remember { mutableStateOf(generateNoiseBitmap(width, height)) }

    LaunchedEffect(Unit) {
        while (true) {
            bitmap = generateNoiseBitmap(width, height)
            delay(50) // 更新間隔（短いほどジジジ感UP）
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawImage(bitmap.asImageBitmap(), dstSize = IntSize(size.width.toInt(), size.height.toInt()))
        }

        Text(
            text = "🚀 起動中...",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// ランダムなノイズ生成
fun generateNoise(): List<Pair<Float, Float>> {
    val points = mutableListOf<Pair<Float, Float>>()
    val numPoints = 1000 // 点の数（多すぎ注意）

    repeat(numPoints) {
        points.add(
            Random.nextFloat() to Random.nextFloat()
        )
    }
    return points
}

// ノイズを描く関数
fun DrawScope.drawNoise(points: List<Pair<Float, Float>>) {
    points.forEach { (x, y) ->
        drawRect(
            color = if (Random.nextBoolean()) Color.White else Color.Gray,
            topLeft = androidx.compose.ui.geometry.Offset(x * size.width, y * size.height),
            size = androidx.compose.ui.geometry.Size(2f, 2f)
        )
    }
}

fun generateNoiseBitmap(width: Int, height: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    for (x in 0 until width) {
        for (y in 0 until height) {
            val color = if (Random.nextBoolean()) GColor.WHITE else GColor.BLACK
            bitmap.setPixel(x, y, color)
        }
    }
    return bitmap
}
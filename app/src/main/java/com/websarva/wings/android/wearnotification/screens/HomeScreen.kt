package com.websarva.wings.android.wearnotification.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.websarva.wings.android.wearnotification.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.compose.ui.res.stringResource
import com.websarva.wings.android.wearnotification.R
import com.websarva.wings.android.wearnotification.ui.*
import com.websarva.wings.android.wearnotification.utills.BatteryUtils.getBatteryLevel
import com.websarva.wings.android.wearnotification.utills.DateUtils.getCurrentTime

import kotlin.random.Random

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    var batteryLevel by remember { mutableStateOf(getBatteryLevel(context)) }
    var flickerTarget by remember { mutableStateOf(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentTime()
            batteryLevel = getBatteryLevel(context)
            delay(1000) // 1秒ごとに更新
            flickerTarget = if (Random.nextBoolean()) 1f else 0.9f
        }
    }

    val flickerAlpha by animateFloatAsState(
        targetValue = flickerTarget,
        animationSpec = tween(300, easing = LinearEasing),
        label = "Flicker"
    )

    val listState = rememberScalingLazyListState()

    ScalingLazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            NixieClock(
                currentTime = currentTime,
                flickerAlpha = flickerAlpha
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ScreenButton(
                navController = navController,
                navigateRoute=Screens.MESSAGEPACK.name,
                buttonText = stringResource(R.string.button_title_message)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ScreenButton(
                navController = navController,
                navigateRoute=Screens.NOTIFICATION.name,
                buttonText = stringResource(R.string.button_title_notification_history)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            NixieBatteryGauge(batteryLevel, flickerAlpha)
        }
    }
}
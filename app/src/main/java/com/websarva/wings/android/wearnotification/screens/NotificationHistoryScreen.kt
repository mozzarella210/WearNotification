package com.websarva.wings.android.wearnotification.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import com.websarva.wings.android.wearnotification.ui.BackTextButton
import com.websarva.wings.android.wearnotification.ui.theme.*
import com.websarva.wings.android.wearnotification.viewmodel.NotificationHistoryViewModel

@Composable
fun NotificationHistoryScreen(
    navController: NavController,
    viewModel: NotificationHistoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadNotificationHistory()
    }

    val listState = rememberScalingLazyListState()

    ScalingLazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            BackTextButton(navController = navController)
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(viewModel.notificationList.value.size) { index ->
            val notification = viewModel.notificationList.value[index]

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(nixieDarkBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, nixieOrange, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "📢 ${notification.title}",
                        fontSize = 18.sp,
                        color = nixieOrange
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = notification.message,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
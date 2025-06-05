package com.websarva.wings.android.wearnotification.screens

import androidx.compose.ui.graphics.Shadow
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.websarva.wings.android.wearnotification.ui.BackTextButton
import com.websarva.wings.android.wearnotification.ui.theme.*
import com.websarva.wings.android.wearnotification.R
import com.websarva.wings.android.wearnotification.ui.NixieButton
import com.websarva.wings.android.wearnotification.viewmodel.MessageViewModel

@Composable
fun MessageScreen(
    navController: NavController,
    viewModel: MessageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val orbitronFont = FontFamily(Font(R.font.orbitron_medium)) // これ使うフォント登録済み前提！

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(nixieBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BackTextButton(navController = navController)

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(2.dp, nixieOrange, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
                .padding(8.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(viewModel.receivedMessages.value.size) { index ->
                    Text(
                        text = viewModel.receivedMessages.value[index],
                        fontSize = 20.sp,
                        color = nixieOrange,
                        fontFamily = orbitronFont,
                        style = TextStyle(
                            shadow = Shadow(
                                color = nixieOrange.copy(alpha = 0.6f),
                                offset = Offset(0f, 0f),
                                blurRadius = 12f
                            )
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF222222),
                                        Color(0xFF111111)
                                    )
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    )
                }
            }

            // 自動スクロール
            LaunchedEffect(viewModel.receivedMessages.value.size) {
                if (viewModel.receivedMessages.value.isNotEmpty()) {
                    listState.animateScrollToItem(viewModel.receivedMessages.value.size - 1)
                }
            }
        }

        // メッセージ入力
        TextField(
            value = viewModel.message.value,
            onValueChange = { viewModel.message.value = it },
            label = { Text("メッセージ", color = nixieOrange) },
            textStyle = TextStyle(color = nixieOrange, fontFamily = orbitronFont),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black,
                focusedIndicatorColor = nixieOrange,
                unfocusedIndicatorColor = nixieOrange.copy(alpha = 0.5f),
                cursorColor = nixieOrange,
                focusedLabelColor = nixieOrange,
                unfocusedLabelColor = nixieOrange
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        NixieButton(
            onClick = { viewModel.sendMessage() },
            buttonText = stringResource(R.string.button_title_send)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.connectWebSocket()
    }
}

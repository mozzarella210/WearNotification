/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.websarva.wings.android.wearnotification.presentation

import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.viewmodel.compose.viewModel
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.firebase.messaging.FirebaseMessaging
import com.websarva.wings.android.wearnotification.R
import com.websarva.wings.android.wearnotification.presentation.theme.WearNotificationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }


        // 🔽ここに追加！
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM_TOKEN", "トークン取得失敗", task.exception)
                return@addOnCompleteListener
            }

            // トークン取得成功！
            val token = task.result
            Log.d("FCM_TOKEN", "取得したトークン: $token")
            Toast.makeText(this, "FCMトークン取得完了！", Toast.LENGTH_SHORT).show()
        }

        setContent {
            //WebSocketScreen()

            val viewModel: MainViewModel = viewModel()

            LaunchedEffect(Unit) {
                delay(3000) // 3秒待つ（必要に応じて調整可）
                FirebaseMessaging.getInstance().subscribeToTopic("all_users")
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("FCM_TOPIC", "トピック購読成功！")
                        } else {
                            Log.e("FCM_TOPIC", "トピック購読失敗！", task.exception)
                        }
                    }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f) // ✅ 画面の残りのスペースを最大限使う
                        .fillMaxWidth()
                        .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)) // ✅ 枠線を明確に
                        .clip(RoundedCornerShape(12.dp)) // ✅ 角を丸くする（必須）
                        .background(Color.White) // ✅ 背景を白に設定して、境界が分かりやすいようにする
                        .padding(8.dp) // ✅ 内側の余白を適切に確保
                ) {
                    val listState = rememberLazyListState()

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp) // ✅ `LazyColumn` に余白を確保して、画面端にくっつかないようにする
                    ) {
                        items(viewModel.receivedMessages.value.size) { index ->
                            Text(
                                text = viewModel.receivedMessages.value[index],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp) // ✅ メッセージごとの間隔を適切に確保
                                    .background(Color.LightGray, RoundedCornerShape(8.dp)) // ✅ メッセージを視認しやすくする
                                    .padding(8.dp) // ✅ メッセージ内のテキストにも適切な余白を追加
                            )
                        }
                    }

                    // ✅ メッセージが追加されたら自動でスクロール
                    LaunchedEffect(viewModel.receivedMessages.value.size) {
                        if (viewModel.receivedMessages.value.isNotEmpty()) {
                            listState.animateScrollToItem(viewModel.receivedMessages.value.size - 1)
                        }
                    }
                }

                TextField(
                    value = viewModel.message.value,
                    onValueChange = { viewModel.message.value = it },
                    label = { Text("メッセージ") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = { viewModel.sendMessage() }) {
                    Text("送信")
                }

            }
            LaunchedEffect(Unit) {
                viewModel.connectWebSocket()
            }
        }
    }

}

@Composable
fun WearApp(greetingName: String) {
    WearNotificationTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            Greeting(greetingName = greetingName)
        }
    }
}

@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )

}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}
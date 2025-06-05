package com.websarva.wings.android.wearnotification.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.websarva.wings.android.wearnotification.common.MyWebSocketListener
import com.websarva.wings.android.wearnotification.common.WebSocketClient
import com.websarva.wings.android.wearnotification.firebase.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.WebSocketListener
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: FirebaseRepository
) : ViewModel() {

    val message = mutableStateOf("")
    val receivedMessages = mutableStateOf(listOf<String>())

    private val webSocketClient = WebSocketClient(
        "ws://192.168.179.11:8765",
        createWebSocketListener(context)
    )

    fun connectWebSocket() {
        webSocketClient.connect()
    }

    fun sendMessage() {
        webSocketClient.sendMessage(message.value)
        message.value = ""
    }

    private fun createWebSocketListener(context: Context): WebSocketListener {
        return MyWebSocketListener(context) { receivedMessage ->
            receivedMessages.value = receivedMessages.value + receivedMessage
        }
    }
}
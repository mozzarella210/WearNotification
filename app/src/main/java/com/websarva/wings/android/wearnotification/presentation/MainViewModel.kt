package com.websarva.wings.android.wearnotification.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.websarva.wings.android.wearnotification.presentation.common.MyWebSocketListener
import com.websarva.wings.android.wearnotification.presentation.common.WebSocketClient
import okhttp3.WebSocketListener

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val message = mutableStateOf("")
    val receivedMessages = mutableStateOf(listOf<String>())

    private val webSocketClient = WebSocketClient(
        "ws://192.168.179.11:8765",
        createWebSocketListener(application)
    )

    fun connectWebSocket() {
        webSocketClient.connect()
    }

    fun sendMessage() {
        webSocketClient.sendMessage(message.value)
        message.value = ""
    }

    private fun createWebSocketListener(context: Application): WebSocketListener {
        return MyWebSocketListener(context) { receivedMessage ->
            receivedMessages.value = receivedMessages.value + receivedMessage
        }
    }
}
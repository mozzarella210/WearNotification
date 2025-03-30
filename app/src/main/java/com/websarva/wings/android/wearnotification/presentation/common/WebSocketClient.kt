package com.websarva.wings.android.wearnotification.presentation.common

import android.util.Log
import okhttp3.*

class WebSocketClient(
    private val url: String,
    private val listener: WebSocketListener
) {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null


    fun connect() {
        Log.d("WebSocket", "接続開始: $url")
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMessage(message: String) {
        Log.d("WebSocket", "送信メッセージ: $message")
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, null)
    }
}
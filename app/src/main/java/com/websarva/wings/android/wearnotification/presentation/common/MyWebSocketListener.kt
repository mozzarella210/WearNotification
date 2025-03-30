package com.websarva.wings.android.wearnotification.presentation.common

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresPermission
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MyWebSocketListener(
    private val context: Context,
    private val onMessageReceived: (String) -> Unit
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        // WebSocket接続が開かれたときの処理
        Log.d("WebSocket", "接続成功")
        vibrateDevice()
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    override fun onMessage(webSocket: WebSocket, text: String) {
        onMessageReceived(text)
        Log.d("WebSocket", "受信: $text")
        vibrateDevice()
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // バイナリメッセージを受信した場合の処理
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        // WebSocket接続が閉じられようとしているときの処理
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        // WebSocket接続が閉じられたときの処理
        Log.d("WebSocket", "接続終了")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        // WebSocket接続に失敗した場合の処理
        Log.e("WebSocket", "エラー: ${t.message}")
        t.printStackTrace()

    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun vibrateDevice() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))

        /*
        VibrationEffect.DEFAULT_AMPLITUDE	デフォルトの振動強度（デバイス依存）
        VibrationEffect.EFFECT_CLICK	短い軽い振動（クリックフィードバック用）
        VibrationEffect.EFFECT_DOUBLE_CLICK	2回の短い振動（ダブルクリック用）
        VibrationEffect.EFFECT_HEAVY_CLICK	強めの短い振動（物理ボタンの押下感）
        VibrationEffect.EFFECT_TICK	軽いタップ感（時計の秒針のような微細な振動）

         */
    }
}
package com.websarva.wings.android.wearnotification.presentation.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.websarva.wings.android.wearnotification.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "タイトルなし"
        val message = remoteMessage.notification?.body ?: "メッセージなし"

        showNotification(title, message)
    }

    private fun showNotification(title: String, message: String) {
        //val channelId = "fcm_default_channel"
        val channelId = "fcm_channel_vibe_strong" // ← 新しいIDに変更！
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 🔥 通知チャンネルを強化バイブで作成（Android 8以上）
        val channel = NotificationChannel(
            channelId,
            "FCM通知",  // ← 設定画面に表示される名前
            NotificationManager.IMPORTANCE_HIGH // ← 高めで振動出やすく！
        ).apply {
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 1500, 300, 1500, 500, 2000) // ← 好きなバイブに変更可！
        }
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.splash_icon)
            .setVibrate(longArrayOf(0, 500, 300, 500, 300, 800))
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

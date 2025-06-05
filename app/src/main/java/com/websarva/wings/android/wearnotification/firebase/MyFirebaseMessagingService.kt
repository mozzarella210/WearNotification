package com.websarva.wings.android.wearnotification.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.common.reflect.TypeToken
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.websarva.wings.android.wearnotification.R
import com.google.gson.Gson

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM_SERVICE", "通知受信: title=${remoteMessage.notification?.title}, message=${remoteMessage.notification?.body}")
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "タイトルなし"
        val message = remoteMessage.notification?.body ?: "メッセージなし"

        showNotification(title, message)
    }

    private fun showNotification(title: String, message: String) {
        saveNotificationToHistory(title, message)

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

    private fun saveNotificationToHistory(title: String, message: String) {
        val prefs = getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
        val gson = Gson()

        val json = prefs.getString("notification_history", "[]")
        val type = object : TypeToken<MutableList<NotificationData>>() {}.type
        val historyList: MutableList<NotificationData> = gson.fromJson(json, type)

        historyList.add(0, NotificationData(title, message))

        val limitedList = historyList.take(5)

        prefs.edit()
            .putString("notification_history", gson.toJson(limitedList))
            .apply()
    }
}

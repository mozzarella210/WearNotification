package com.websarva.wings.android.wearnotification.health

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.websarva.wings.android.wearnotification.R

class HeartRateMonitorService : Service(), SensorEventListener {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "heart_rate_monitor_channel"
    }

    private lateinit var sensorManager: SensorManager
    private var heartRateSensor: Sensor? = null

    override fun onCreate() {
        super.onCreate()

        // センサー初期化
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        heartRateSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        startForegroundServiceWithNotification()
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundServiceWithNotification() {
        val channelId = "heart_rate_monitor_channel"
        val serviceChannel = NotificationChannel(
            channelId,
            "Heart Rate Monitor Service",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("脈拍モニタリング中")
            .setContentText("異常な心拍数を監視しています")
            .setSmallIcon(R.drawable.ic_heart)
            .build()

        startForeground(
            NOTIFICATION_ID,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH // ← これがないと今エラーになる！
        )
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_HEART_RATE) {
                val heartRate = it.values[0].toInt()

                // 異常判定
                if (heartRate > 120 || heartRate < 40) {
                    showAbnormalNotification(heartRate)
                }
            }
        }
    }

    private fun showAbnormalNotification(heartRate: Int) {
        val channelId = "heart_rate_alert_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val alertChannel = NotificationChannel(
            channelId,
            "Heart Rate Alerts",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "心拍数異常通知チャンネル"
            enableVibration(true)
        }
        notificationManager.createNotificationChannel(alertChannel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("⚠️ 異常な心拍数検知！")
            .setContentText("現在の心拍数: $heartRate bpm")
            .setSmallIcon(R.drawable.ic_heart)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(2, notification)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 精度変更は特にいらない
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

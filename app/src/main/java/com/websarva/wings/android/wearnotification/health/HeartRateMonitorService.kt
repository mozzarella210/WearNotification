package com.websarva.wings.android.wearnotification.health

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class HeartRateMonitorService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var heartRateSensor: Sensor? = null

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        heartRateSensor?.let {
            sensorManager.registerListener(this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_HEART_RATE) {
                val heartRate = it.values[0]
                Log.d("HeartRateMonitor", "Heart Rate: $heartRate")

                // 異常検知ロジック
                if (heartRate > 120 || heartRate < 40) { // 💥 異常とみなす閾値
                    showNotification(heartRate.toInt())
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun showNotification(heartRate: Int) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "heart_rate_alert"

        val channel = NotificationChannel(
            channelId,
            "Heart Rate Alerts",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        /*
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("⚠️ 異常な心拍数")
            .setContentText("現在の心拍数: $heartRate bpm")
            .setSmallIcon(R.drawable.ic_heart) // アイコンは適当に差し替えてね
            .build()


         */
        /*
        notificationManager.notify(1001, notification)

         */
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

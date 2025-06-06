package com.websarva.wings.android.wearnotification

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.websarva.wings.android.wearnotification.health.HeartRateMonitorService
import com.websarva.wings.android.wearnotification.screens.*
import com.websarva.wings.android.wearnotification.screens.SplashScreen
import com.websarva.wings.android.wearnotification.ui.theme.WearNotificationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BODY_SENSORS), 1002)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }

        val serviceIntent = Intent(this, HeartRateMonitorService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)

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
            WearNotificationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    WearNavigation()
                }
            }
        }
    }
}

@Composable
fun WearNavigation() {

    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Screens.HOME.name)
    {
        composable(route = Screens.SPLASH.name) {
            SplashScreen(navController)
        }

        composable(route = Screens.HOME.name) {
            HomeScreen(navController)
        }

        composable(route = Screens.MESSAGEPACK.name) {
            MessagePackScreen(navController)
        }

        composable(route = Screens.MESSAGE.name) {
            MessageScreen(navController)
        }

        composable(route = Screens.NOTIFICATION.name) {
            NotificationHistoryScreen(navController)
        }
    }
}
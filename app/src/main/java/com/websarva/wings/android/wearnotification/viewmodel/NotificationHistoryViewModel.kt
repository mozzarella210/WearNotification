package com.websarva.wings.android.wearnotification.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.common.reflect.TypeToken
import com.websarva.wings.android.wearnotification.firebase.FirebaseRepository
import com.websarva.wings.android.wearnotification.firebase.NotificationData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.google.gson.Gson

@HiltViewModel
class NotificationHistoryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: FirebaseRepository
) : ViewModel() {

    val notificationList = mutableStateOf<List<NotificationData>>(emptyList())

    fun loadNotificationHistory() {
        val prefs = context.getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
        val gson = Gson()

        val json = prefs.getString("notification_history", "[]")
        val type = object : TypeToken<List<NotificationData>>() {}.type
        val historyList: List<NotificationData> = gson.fromJson(json, type)

        notificationList.value = historyList
    }
}
package com.websarva.wings.android.wearnotification.utills

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HHmmss", Locale.getDefault())
        return sdf.format(Date())
    }
}
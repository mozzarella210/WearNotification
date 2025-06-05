package com.websarva.wings.android.wearnotification.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.websarva.wings.android.wearnotification.common.MyWebSocketListener
import com.websarva.wings.android.wearnotification.common.WebSocketClient
import com.websarva.wings.android.wearnotification.firebase.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.WebSocketListener
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: FirebaseRepository
) : ViewModel() {


}
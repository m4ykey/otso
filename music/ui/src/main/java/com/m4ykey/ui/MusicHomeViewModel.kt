package com.m4ykey.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.m4ykey.core.notification.isNotificationListenerEnabled

class MusicHomeViewModel : ViewModel() {

    private val _isNotificationAccessGranted = MutableLiveData<Boolean>()
    val isNotificationAccessGranted : LiveData<Boolean> get() = _isNotificationAccessGranted

    fun checkNotificationAccess(context: Context) {
        val isGranted = isNotificationListenerEnabled(context)
        _isNotificationAccessGranted.value = isGranted
    }

}
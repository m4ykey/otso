package com.m4ykey.core.helpers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object MusicNotificationState {
    private val _songInfo = MutableStateFlow<String?>(null)
    val songInfo : StateFlow<String?> = _songInfo

    fun updateSongInfo(info : String) {
        _songInfo.value = info
    }
}
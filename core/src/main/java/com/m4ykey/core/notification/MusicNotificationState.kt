package com.m4ykey.core.notification

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object MusicNotificationState {

    private val _title = MutableStateFlow<String?>(null)
    val title : StateFlow<String?> = _title

    private val _artist = MutableStateFlow<String?>(null)
    val artist : StateFlow<String?> = _artist

    fun updateTitle(info : String) {
        _title.value = info
    }

    fun updateArtist(info : String) {
        _artist.value = info
    }

}
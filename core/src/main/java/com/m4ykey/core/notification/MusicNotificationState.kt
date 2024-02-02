package com.m4ykey.core.notification

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object MusicNotificationState {

    private val _title = MutableStateFlow<String?>(null)
    val title : StateFlow<String?> = _title

    private val _artist = MutableStateFlow<String?>(null)
    val artist : StateFlow<String?> = _artist

    private val _app = MutableStateFlow<String?>(null)
    val app : StateFlow<String?> = _app

    private val _subText = MutableStateFlow<String?>(null)
    val subText : StateFlow<String?> = _subText

    fun updateSubText(info : String) {
        _subText.value = info
    }

    fun updateAppInfo(info : String) {
        _app.value = info
    }

    fun updateTitle(info : String) {
        _title.value = info
    }

    fun updateArtist(info : String) {
        _artist.value = info
    }

}
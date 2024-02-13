package com.m4ykey.ui.lyrics.uistate

import com.m4ykey.data.domain.model.lyrics.TrackMessage

data class LyricsUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val lyrics : TrackMessage? = null
)
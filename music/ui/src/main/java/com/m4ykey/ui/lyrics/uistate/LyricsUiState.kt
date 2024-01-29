package com.m4ykey.ui.lyrics.uistate

import com.m4ykey.data.domain.model.lyrics.Song

data class LyricsUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val result : Song? = null
)
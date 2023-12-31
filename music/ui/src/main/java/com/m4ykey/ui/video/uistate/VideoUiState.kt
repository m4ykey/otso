package com.m4ykey.ui.video.uistate

import com.m4ykey.data.domain.model.video.VideoItem

data class VideoUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val videos : List<VideoItem> = emptyList()
)

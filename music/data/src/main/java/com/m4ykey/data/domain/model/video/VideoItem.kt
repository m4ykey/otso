package com.m4ykey.data.domain.model.video

import androidx.compose.runtime.Immutable

@Immutable
data class VideoItem(
    val id: String? = "",
    val snippet: Snippet? = null
)

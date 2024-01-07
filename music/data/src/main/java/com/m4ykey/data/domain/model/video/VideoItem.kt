package com.m4ykey.data.domain.model.video

import androidx.compose.runtime.Immutable

@Immutable
data class VideoItem(
    val contentDetails: ContentDetails? = null,
    val id: String? = "",
    val snippet: Snippet? = null,
    val statistics: Statistics? = null
)

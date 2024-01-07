package com.m4ykey.data.domain.model.video

import androidx.compose.runtime.Immutable

@Immutable
data class Snippet(
    val categoryId: String? = "",
    val channelId: String? = "",
    val channelTitle: String? = "",
    val defaultAudioLanguage: String? = "",
    val description: String? = "",
    val liveBroadcastContent: String? = "",
    val publishedAt: String? = "",
    val tags: List<String>? = emptyList(),
    val thumbnails: Thumbnails? = null,
    val title: String? = ""
)
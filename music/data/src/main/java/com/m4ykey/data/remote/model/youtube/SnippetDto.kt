package com.m4ykey.data.remote.model.youtube

data class SnippetDto(
    val categoryId: String? = "",
    val channelId: String? = "",
    val channelTitle: String? = "",
    val defaultAudioLanguage: String? = "",
    val description: String? = "",
    val liveBroadcastContent: String? = "",
    val publishedAt: String? = "",
    val tags: List<String>? = emptyList(),
    val thumbnails: ThumbnailsDto? = null,
    val title: String? = ""
)
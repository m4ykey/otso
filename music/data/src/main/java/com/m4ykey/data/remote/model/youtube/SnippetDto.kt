package com.m4ykey.data.remote.model.youtube

data class SnippetDto(
    val channelId: String,
    val channelTitle: String,
    val thumbnails: ThumbnailsDto,
    val title: String
)
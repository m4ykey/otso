package com.m4ykey.data.remote.model.youtube

data class VideoItemDto(
    val contentDetails: ContentDetailsDto? = null,
    val id: String? = "",
    val snippet: SnippetDto? = null,
    val statistics: StatisticsDto? = null
)
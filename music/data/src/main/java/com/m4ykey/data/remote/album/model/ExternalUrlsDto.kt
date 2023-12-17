package com.m4ykey.data.remote.album.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExternalUrlsDto(
    val spotify: String
)
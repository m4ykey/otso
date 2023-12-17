package com.m4ykey.data.remote.album.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDto(
    val height: Int? = 0,
    val url: String? = "",
    val width: Int? = 0
)
package com.m4ykey.data.remote.album.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDto(
    val height: Int,
    val url: String,
    val width: Int
)
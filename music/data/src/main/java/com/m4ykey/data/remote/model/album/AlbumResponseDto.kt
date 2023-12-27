package com.m4ykey.data.remote.model.album

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumResponseDto(
    val albums: AlbumsDto
)
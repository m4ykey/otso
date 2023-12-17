package com.m4ykey.data.remote.album.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumResponseDto(
    val albums: AlbumsDto
)
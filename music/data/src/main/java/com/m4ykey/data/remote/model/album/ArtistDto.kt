package com.m4ykey.data.remote.model.album

data class ArtistDto(
    val externalUrls: ExternalUrlsDto? = null,
    val id: String,
    val name: String
)
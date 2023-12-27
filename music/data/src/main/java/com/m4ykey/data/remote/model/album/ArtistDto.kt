package com.m4ykey.data.remote.model.album

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtistDto(
    val externalUrls: ExternalUrlsDto?,
    val id: String? = "",
    val name: String? = ""
)
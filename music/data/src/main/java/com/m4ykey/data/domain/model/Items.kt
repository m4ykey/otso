package com.m4ykey.data.domain.model

data class Items(
    val albumType: String? = "",
    val artists: List<Artist>? = emptyList(),
    val externalUrls: ExternalUrls? = null,
    val id: String? = "",
    val images: List<Image>? = emptyList(),
    val name: String? = "",
    val releaseDate: String? = "",
    val totalTracks: Int? = 0
)
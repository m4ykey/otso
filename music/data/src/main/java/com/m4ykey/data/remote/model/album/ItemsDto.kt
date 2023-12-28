package com.m4ykey.data.remote.model.album

data class ItemsDto(
    val album_type: String? = "",
    val artists: List<ArtistDto>,
    val external_urls: ExternalUrlsDto,
    val id: String? = "",
    val images: List<ImageDto>,
    val name: String? = "",
    val release_date: String? = "",
    val total_tracks: Int? = 0
)
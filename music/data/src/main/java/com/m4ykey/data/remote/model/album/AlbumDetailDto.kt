package com.m4ykey.data.remote.model.album

data class AlbumDetailDto(
    val album_type: String,
    val artists: List<ArtistDto>,
    val copyrights: List<CopyrightDto>,
    val external_urls: ExternalUrlsDto,
    val id: String,
    val images: List<ImageDto>,
    val label: String,
    val name: String,
    val popularity: Int,
    val release_date: String? = "",
    val total_tracks: Int
)
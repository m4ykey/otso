package com.m4ykey.data.remote.album.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemsDto(
    @field:Json(name = "album_type") val albumType: String,
    val artists: List<ArtistDto>,
    @field:Json(name = "external_urls") val externalUrls: ExternalUrlsDto,
    val id: String,
    val images: List<ImageDto>,
    val name: String,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "total_tracks") val totalTracks: Int
)
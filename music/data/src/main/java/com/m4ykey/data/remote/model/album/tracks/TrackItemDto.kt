package com.m4ykey.data.remote.model.album.tracks

import com.m4ykey.data.remote.model.album.ArtistDto
import com.m4ykey.data.remote.model.album.ExternalUrlsDto

data class TrackItemDto(
    val artists: List<ArtistDto>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_urls: ExternalUrlsDto,
    val id: String,
    val name: String,
    val preview_url: String? = "",
    val track_number: Int
)
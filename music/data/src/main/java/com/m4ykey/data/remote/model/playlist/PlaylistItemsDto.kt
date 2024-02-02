package com.m4ykey.data.remote.model.playlist

import com.m4ykey.data.remote.model.album.ExternalUrlsDto

data class PlaylistItemsDto(
    val description: String,
    val external_urls: ExternalUrlsDto,
    val id: String,
    val images: List<PlaylistImageDto>,
    val name: String,
    val primary_color: String
    //val tracks: Tracks
)
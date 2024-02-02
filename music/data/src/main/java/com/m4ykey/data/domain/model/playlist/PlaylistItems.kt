package com.m4ykey.data.domain.model.playlist

import com.m4ykey.data.domain.model.album.ExternalUrls

data class PlaylistItems(
    val description: String,
    val externalUrls: ExternalUrls,
    val id: String,
    val images: List<PlaylistImage>,
    val name: String,
    val primaryColor: String
)

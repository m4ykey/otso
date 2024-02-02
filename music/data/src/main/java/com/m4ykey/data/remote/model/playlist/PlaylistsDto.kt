package com.m4ykey.data.remote.model.playlist

data class PlaylistsDto(
    val items: List<PlaylistItemsDto>,
    val next: String? = null,
    val previous: String? = null,
)
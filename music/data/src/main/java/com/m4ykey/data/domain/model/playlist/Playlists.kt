package com.m4ykey.data.domain.model.playlist

data class Playlists(
    val items : List<PlaylistItems>,
    val next : String,
    val previous : String
)
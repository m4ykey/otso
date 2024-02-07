package com.m4ykey.data.domain.model.playlist

import androidx.compose.runtime.Immutable

@Immutable
data class Playlists(
    val items : List<PlaylistItems>,
    val next : String,
    val previous : String
)
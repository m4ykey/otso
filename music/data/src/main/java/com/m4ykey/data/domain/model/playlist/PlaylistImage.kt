package com.m4ykey.data.domain.model.playlist

import androidx.compose.runtime.Immutable

@Immutable
data class PlaylistImage(
    val height : String,
    val url : String,
    val width : String
)

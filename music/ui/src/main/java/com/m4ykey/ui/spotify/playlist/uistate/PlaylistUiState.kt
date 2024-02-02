package com.m4ykey.ui.spotify.playlist.uistate

import com.m4ykey.data.domain.model.playlist.PlaylistItems

data class PlaylistUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val playlist : List<PlaylistItems> = emptyList()
)

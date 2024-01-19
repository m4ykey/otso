package com.m4ykey.ui.spotify.album.uistate

import com.m4ykey.data.domain.model.album.Items

data class AlbumUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val albums : List<Items> = emptyList()
)

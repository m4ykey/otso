package com.m4ykey.ui.spotify.artist.uistate

import com.m4ykey.data.domain.model.artist.Artist

data class ArtistUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val artist : Artist? = null
)

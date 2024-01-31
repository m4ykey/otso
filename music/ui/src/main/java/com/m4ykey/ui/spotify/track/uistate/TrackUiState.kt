package com.m4ykey.ui.spotify.track.uistate

import com.m4ykey.data.domain.model.track.Track

data class TrackUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val result : List<Track> = emptyList()
)

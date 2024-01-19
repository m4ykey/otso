package com.m4ykey.ui.spotify.album.uistate

import androidx.paging.compose.LazyPagingItems
import com.m4ykey.data.domain.model.album.tracks.TrackItem

data class CombinedAlbumState(
    val albumDetailState : AlbumDetailUiState? = null,
    val lazyPagingItems : LazyPagingItems<TrackItem>
)

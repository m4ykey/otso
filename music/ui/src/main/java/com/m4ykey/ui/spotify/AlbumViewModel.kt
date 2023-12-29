package com.m4ykey.ui.spotify

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.ui.spotify.uistate.AlbumDetailUiState
import com.m4ykey.ui.spotify.uistate.AlbumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    private val _albumUiState = MutableStateFlow(AlbumUiState())
    val albumUiState = _albumUiState.asStateFlow()

    private val _albumDetailUiState = MutableStateFlow(AlbumDetailUiState())
    val albumDetailUiState = _albumDetailUiState.asStateFlow()

    init {
        viewModelScope.launch { getNewReleases() }
    }

    suspend fun getAlbumById(albumId : String) {
        repository.getAlbumById(albumId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _albumDetailUiState.value = AlbumDetailUiState(
                        isLoading = false,
                        albumDetail = result.data
                    )
                }
                is Resource.Error -> {
                    _albumDetailUiState.value = AlbumDetailUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
                is Resource.Loading -> {
                    _albumDetailUiState.value = AlbumDetailUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getNewReleases() {
        repository.getNewReleases().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _albumUiState.value = AlbumUiState(
                        isLoading = false,
                        albums = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _albumUiState.value = AlbumUiState(isLoading = true)
                }
                is Resource.Error -> {
                    _albumUiState.value = AlbumUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    @Composable
    fun observePagingFlow(): LazyPagingItems<Items> {
        return repository.getNewReleasePager().flow.collectAsLazyPagingItems()
    }

    @Composable
    fun observePagingTrackList(albumId: String) : LazyPagingItems<TrackItem> {
        return repository.getTrackListPager(albumId).flow.collectAsLazyPagingItems()
    }

}
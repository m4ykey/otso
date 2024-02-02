package com.m4ykey.ui.spotify.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.ui.spotify.album.uistate.AlbumDetailUiState
import com.m4ykey.ui.spotify.album.uistate.AlbumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    private val _albums = MutableStateFlow(AlbumUiState())
    val albumUiState = _albums.asStateFlow()

    private val _detail = MutableStateFlow(AlbumDetailUiState())
    val albumDetailUiState = _detail.asStateFlow()

    init {
        viewModelScope.launch { getNewReleases() }
    }

    suspend fun getAlbumById(albumId : String) {
        repository.getAlbumById(albumId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _detail.value = AlbumDetailUiState(
                        isLoading = false,
                        albumDetail = result.data
                    )
                }
                is Resource.Error -> {
                    _detail.value = AlbumDetailUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
                is Resource.Loading -> {
                    _detail.value = AlbumDetailUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getNewReleases() {
        repository.getNewReleases().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _albums.value = AlbumUiState(
                        isLoading = false,
                        albums = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _albums.value = AlbumUiState(isLoading = true)
                }
                is Resource.Error -> {
                    _albums.value = AlbumUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPagingNewReleases(): Flow<PagingData<Items>> {
        return repository.getNewReleasePager().cachedIn(viewModelScope)
    }

    fun getPagingTrackList(albumId: String) : Flow<PagingData<TrackItem>> {
        return repository.getTrackListPager(albumId).cachedIn(viewModelScope)
    }

}
package com.m4ykey.ui.spotify.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.playlist.PlaylistItems
import com.m4ykey.data.domain.repository.PlaylistRepository
import com.m4ykey.ui.spotify.playlist.uistate.PlaylistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val repository: PlaylistRepository
) : ViewModel() {

    private val _playlist = MutableStateFlow(PlaylistUiState())
    val playlist = _playlist.asStateFlow()

    init {
        viewModelScope.launch { getFeaturedPlaylist() }
    }

    private suspend fun getFeaturedPlaylist() {
        repository.getFeaturedPlaylists().onEach { result ->
            when (result) {
                is Resource.Loading -> { _playlist.value = PlaylistUiState(isLoading = true)
                }
                is Resource.Success -> {
                    _playlist.value = PlaylistUiState(
                        isLoading = false,
                        playlist = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _playlist.value = PlaylistUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPagingFeaturedPlaylist() : Flow<PagingData<PlaylistItems>> {
        return repository.getFeaturedPlaylistPager().cachedIn(viewModelScope)
    }

}
package com.m4ykey.ui.spotify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.ui.spotify.uistate.AlbumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    private val _albumUiState = MutableStateFlow(AlbumUiState())
    val albumUiState = _albumUiState.asStateFlow()

    suspend fun getNewReleases() {
        repository.getNewReleases().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _albumUiState.value = albumUiState.value.copy(
                        isLoading = false,
                        albums = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _albumUiState.value = albumUiState.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _albumUiState.value = albumUiState.value.copy(
                        error = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}
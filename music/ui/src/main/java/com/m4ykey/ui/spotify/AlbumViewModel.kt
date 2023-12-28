package com.m4ykey.ui.spotify

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.repository.AlbumRepository
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch { getNewReleases() }
    }

    private suspend fun getNewReleases() {
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
                        isLoading = true,
                        albums = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _albumUiState.value = albumUiState.value.copy(
                        error = result.message ?: "Unknown error",
                        isLoading = false,
                        albums = result.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    @Composable
    fun observePagingFlow(): LazyPagingItems<Items> {
        return repository.getNewReleasePager().flow.collectAsLazyPagingItems()
    }

}
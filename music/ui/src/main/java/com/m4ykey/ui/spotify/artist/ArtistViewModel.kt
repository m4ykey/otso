package com.m4ykey.ui.spotify.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.repository.ArtistRepository
import com.m4ykey.ui.spotify.artist.uistate.ArtistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repository: ArtistRepository
) : ViewModel() {

    private val _artist = MutableStateFlow(ArtistUiState())
    val artist : StateFlow<ArtistUiState> get() = _artist

    suspend fun getArtistById(id : String) {
        repository.getArtistById(id).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _artist.value = ArtistUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error",
                        artist = null
                    )
                }
                is Resource.Success -> {
                    _artist.value = ArtistUiState(
                        isLoading = false,
                        error = null,
                        artist = result.data
                    )
                }
                is Resource.Loading -> _artist.value = ArtistUiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

}
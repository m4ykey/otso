package com.m4ykey.ui.spotify.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.repository.TrackRepository
import com.m4ykey.ui.spotify.track.uistate.TrackUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val repository : TrackRepository
) : ViewModel() {

    private var _track = MutableStateFlow(TrackUiState())
    val track = _track.asStateFlow()

    suspend fun getTrackImage(query : String) {
        repository.getTrackImage(query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _track.value = TrackUiState(
                        isLoading = false,
                        result = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _track.value = TrackUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
                is Resource.Loading -> {
                    _track.value = TrackUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}
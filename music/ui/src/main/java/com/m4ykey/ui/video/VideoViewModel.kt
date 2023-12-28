package com.m4ykey.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.repository.VideoRepository
import com.m4ykey.ui.video.uistate.VideoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private val _videoUiState = MutableStateFlow(VideoUiState())
    val videoUiState = _videoUiState.asStateFlow()

    init {
        viewModelScope.launch { getMostPopularVideos() }
    }

    private suspend fun getMostPopularVideos() {
        repository.getMostPopularVideos().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _videoUiState.value = VideoUiState(
                        isLoading = false,
                        videos = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _videoUiState.value = VideoUiState(isLoading = true)
                }
                is Resource.Error -> {
                    _videoUiState.value = VideoUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}
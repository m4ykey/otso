package com.m4ykey.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.repository.VideoRepository
import com.m4ykey.ui.video.uistate.VideoDetailUiState
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

    private val _videoDetailUiState = MutableStateFlow(VideoDetailUiState())
    val videoDetailUiState = _videoDetailUiState.asStateFlow()

    init {
        viewModelScope.launch { getMostPopularVideos() }
    }

    suspend fun getVideoDetails(videoId : String) {
        repository.getVideoDetails(videoId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _videoDetailUiState.value = VideoDetailUiState(
                        isLoading = false,
                        videos = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _videoDetailUiState.value = VideoDetailUiState(isLoading = true)
                }
                is Resource.Error -> {
                    _videoDetailUiState.value = VideoDetailUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)
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
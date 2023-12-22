package com.m4ykey.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.repository.NewsRepository
import com.m4ykey.data.mappers.toArticle
import com.m4ykey.ui.uistate.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsUiState = MutableStateFlow(NewsUiState())
    val newsUiState = _newsUiState.asStateFlow()

    val pagingFlow = repository.getNewsPager()
        .flow
        .map { pagingData ->
            pagingData.map { it.toArticle() }
        }.cachedIn(viewModelScope)

    init {
        viewModelScope.launch { getLatestNews() }
    }

    private suspend fun getLatestNews() {
        repository.getLatestNews(page = 1, pageSize = 3).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _newsUiState.value = newsUiState.value.copy(
                        news = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _newsUiState.value = newsUiState.value.copy(
                        isLoading = true,
                        news = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _newsUiState.value = newsUiState.value.copy(
                        error = result.message ?: "Unknown error",
                        news = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshNews() {
        viewModelScope.launch { getLatestNews() }
    }

}
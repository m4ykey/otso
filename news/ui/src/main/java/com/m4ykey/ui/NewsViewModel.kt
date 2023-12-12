package com.m4ykey.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.network.Resource
import com.m4ykey.core.paging.Paginator
import com.m4ykey.data.remote.repository.NewsRepository
import com.m4ykey.ui.uistate.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    var newsUiState by mutableStateOf(NewsUiState())

    private val _latestNewsUiState = MutableStateFlow(NewsUiState())
    val latestNewsUiState = _latestNewsUiState.asStateFlow()

    private val newsPaging = Paginator(
        initialKey = newsUiState.page,
        onLoadUpdated = { isLoading ->
            newsUiState = newsUiState.copy(isLoading = isLoading)
        },
        onRequest = { nextPage ->
            repository.getNews(page = nextPage, pageSize = 10)
        },
        getNextKey = {
            newsUiState.page + 1
        },
        onError = { message ->
            newsUiState = newsUiState.copy(error = message!!.localizedMessage)
        },
        onSuccess = { news, newKey ->
            newsUiState = newsUiState.copy(
                news = newsUiState.news + news,
                page = newKey,
                endReached = news.isEmpty()
            )
        }
    )

    suspend fun getLatestNews() {
        repository.getLatestNews(
            page = 1,
            pageSize = 10
        ).onEach { result ->
            _latestNewsUiState.tryEmit(
                when (result) {
                    is Resource.Error -> NewsUiState(error = result.message ?: "Unknown error")
                    is Resource.Loading -> NewsUiState(isLoading = true)
                    is Resource.Success -> NewsUiState(news = result.data ?: emptyList())
                }
            )
        }.launchIn(viewModelScope)
    }

    init {
        viewModelScope.launch { getNextNews() }
    }

    suspend fun getNextNews() {
        viewModelScope.launch { newsPaging.loadNextItem() }
    }

}
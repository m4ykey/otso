package com.m4ykey.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.paging.Paginator
import com.m4ykey.data.remote.repository.NewsRepository
import com.m4ykey.ui.uistate.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    var newsUiState by mutableStateOf(NewsUiState())

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

    init {
        viewModelScope.launch { getNextNews() }
    }

    suspend fun getNextNews() {
        viewModelScope.launch { newsPaging.loadNextItem() }
    }

}
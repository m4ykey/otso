package com.m4ykey.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.m4ykey.data.domain.repository.NewsRepository
import com.m4ykey.data.mappers.toArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    repository: NewsRepository
) : ViewModel() {

    val pagingFlow = repository.getNewsPager()
        .flow
        .map { pagingData ->
            pagingData.map { it.toArticle() }
        }.cachedIn(viewModelScope)
}
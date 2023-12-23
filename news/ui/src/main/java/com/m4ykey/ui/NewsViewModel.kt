package com.m4ykey.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.m4ykey.data.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    repository: NewsRepository
) : ViewModel() {

    val pagingFlow = repository.getNewsPager()
        .flow
        .cachedIn(viewModelScope)
}
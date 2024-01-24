package com.m4ykey.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.data.domain.model.Article
import com.m4ykey.data.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    fun getPagingNews() : Flow<PagingData<Article>> {
        return repository.getNewsPager().cachedIn(viewModelScope)
    }
}
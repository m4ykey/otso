package com.m4ykey.ui.uistate

import com.m4ykey.data.remote.model.Article

data class NewsUiState(
    val page : Int = 1,
    val news : List<Article> = emptyList(),
    val error : String? = null,
    val isLoading : Boolean = false,
    val endReached : Boolean = false
)
package com.m4ykey.ui.uistate

import com.m4ykey.data.domain.model.Article

data class NewsUiState(
    val error : String? = null,
    val isLoading : Boolean = false,
    val news : List<Article> = emptyList()
)

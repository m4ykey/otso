package com.m4ykey.ui.lyrics.uistate

import com.m4ykey.data.domain.model.lyrics.SongResult

data class SearchUiState(
    val isLoading : Boolean? = false,
    val error : String? = null,
    val search : List<SongResult>? = emptyList()
)

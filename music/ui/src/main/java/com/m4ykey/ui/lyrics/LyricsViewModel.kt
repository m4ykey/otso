package com.m4ykey.ui.lyrics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.repository.LyricsRepository
import com.m4ykey.ui.lyrics.uistate.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LyricsViewModel @Inject constructor(
    private val repository : LyricsRepository
) : ViewModel() {

    private val _search = MutableStateFlow(SearchUiState())
    val search = _search.asStateFlow()

    suspend fun searchLyrics(query : String) {
        repository.searchLyrics(query).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _search.value = SearchUiState(isLoading = true)
                }
                is Resource.Success -> {
                    _search.value = SearchUiState(
                        isLoading = false,
                        result = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _search.value = SearchUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}
package com.m4ykey.ui.lyrics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.repository.LyricsRepository
import com.m4ykey.ui.lyrics.uistate.LyricsUiState
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

    private val _lyrics = MutableStateFlow(LyricsUiState())
    val lyrics = _lyrics.asStateFlow()

    private val _search = MutableStateFlow(SearchUiState())

    private suspend fun getLyrics(id : Int) {
        repository.getLyrics(id).onEach { result ->
            when (result) {
                is Resource.Loading -> { _lyrics.value = LyricsUiState(isLoading = true) }
                is Resource.Success -> {
                    _lyrics.value = LyricsUiState(
                        isLoading = false,
                        result = result.data!!
                    )
                }
                is Resource.Error -> {
                    _lyrics.value = LyricsUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun searchLyrics(query : String) {
        repository.searchLyrics(query).onEach { result ->
            when (result) {
                is Resource.Loading -> { _search.value = SearchUiState(isLoading = true) }
                is Resource.Error -> {
                    _search.value = SearchUiState(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
                is Resource.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        _lyrics.value = LyricsUiState(
                            isLoading = false,
                            result = null,
                            error = "No results found"
                        )
                        return@onEach
                    }
                    val firstSongResult = result.data!!.firstOrNull()
                    getLyrics(firstSongResult!!.id)
                }
            }
        }.launchIn(viewModelScope)
    }

}
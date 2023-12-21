package com.m4ykey.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.ui.components.AlbumCard

@Composable
fun NewReleaseHomeScreen(
    modifier : Modifier = Modifier
) {
    val viewModel : AlbumViewModel = hiltViewModel()
    val state by viewModel.albumUiState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.getNewReleases()
    }

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }
        state.error != null -> {
            Text(text = state.error.toString())
        }
        else -> {
            LazyRow(
                modifier = modifier.fillMaxWidth()
            ) {
                items(state.albums) { album ->
                    AlbumCard(item = album)
                }
            }
        }
    }
}
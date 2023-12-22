package com.m4ykey.ui.spotify

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.ui.spotify.components.AlbumCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewReleaseHomeScreen(
    modifier : Modifier = Modifier
) {
    val viewModel : AlbumViewModel = hiltViewModel()
    val state by viewModel.albumUiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel) {
        viewModel.getNewReleases()
    }

    LaunchedEffect(viewModel.albumUiState) {
        snapshotFlow { viewModel.albumUiState }
            .collectLatest { state ->
                state.value.error?.let { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
    }

    Row(modifier = modifier) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        LazyRow(modifier = modifier.weight(1f)) {
            items(state.albums) { album ->
                AlbumCard(item = album)
            }
        }
    }
}
package com.m4ykey.ui.spotify

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.ui.spotify.components.AlbumCard

@Composable
fun NewReleaseHomeScreen(
    modifier : Modifier = Modifier
) {
    val viewModel : AlbumViewModel = hiltViewModel()
    val state by viewModel.albumUiState.collectAsState()
    val context = LocalContext.current

    state.error?.let { error ->
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    if (state.isLoading) {
        CircularProgressIndicator()
    }
    LazyRow(modifier = modifier.fillMaxWidth()) {
        items(state.albums) { album ->
            AlbumCard(item = album)
        }
    }
}
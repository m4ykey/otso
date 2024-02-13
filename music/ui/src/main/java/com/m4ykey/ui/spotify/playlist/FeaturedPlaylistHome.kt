package com.m4ykey.ui.spotify.playlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.ui.components.ErrorScreen
import com.m4ykey.ui.components.ItemRowList
import com.m4ykey.ui.components.PlaylistCard

@Composable
fun FeaturedPlaylistHome(
    onPlaylistClick: (String) -> Unit = {},
    viewModel: PlaylistViewModel = hiltViewModel()
) {

    val state by viewModel.playlist.collectAsState()

    when {
        state.isLoading -> LoadingMaxWidth()
        state.error != null -> ErrorScreen(error = state.error!!)
        state.playlist.isNotEmpty() -> {
            ItemRowList(
                itemList = state.playlist,
                onItemClick = { playlist -> onPlaylistClick(playlist.id) }
            ) { playlist, _ ->
                PlaylistCard(playlist = playlist, onPlaylistClick = onPlaylistClick, size = 120.dp)
            }
        }
        else -> {}
    }

}
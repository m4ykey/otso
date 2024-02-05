package com.m4ykey.ui.spotify.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast
import com.m4ykey.ui.components.PlaylistCard

@Composable
fun FeaturedPlaylistHome(
    modifier: Modifier = Modifier,
    onPlaylistClick: (String) -> Unit = {},
    viewModel: PlaylistViewModel = hiltViewModel()
) {

    val state by viewModel.playlist.collectAsState()
    val context = LocalContext.current

    when {
        state.isLoading -> LoadingMaxWidth()
        state.error != null -> showToast(context, state.error!!)
        else -> {
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    state.playlist,
                    key = { it.id }
                ) { playlist ->
                    PlaylistCard(
                        playlist = playlist,
                        onPlaylistClick = onPlaylistClick,
                        size = 120.dp
                    )
                }
            }
        }
    }

}
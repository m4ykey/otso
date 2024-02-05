package com.m4ykey.ui.spotify.album

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast
import com.m4ykey.ui.components.AlbumCard
import com.m4ykey.ui.components.ItemRowList

@Composable
fun NewReleaseHome(
    onAlbumClick: (String) -> Unit,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val state by viewModel.albumUiState.collectAsState()
    val context = LocalContext.current

    when {
        state.isLoading -> { LoadingMaxWidth() }
        state.error != null -> { showToast(context, state.error!!) }
        state.albums.isNotEmpty() -> {
            ItemRowList(
                itemList = state.albums,
                onItemClick = { album -> onAlbumClick(album.id) }
            ) { album, _ ->
                AlbumCard(item = album, size = 120.dp, onAlbumClick = onAlbumClick)
            }
        }
        else -> {}
    }
}
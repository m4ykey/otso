package com.m4ykey.ui.spotify.playlist

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.data.domain.model.playlist.PlaylistItems
import com.m4ykey.ui.R
import com.m4ykey.ui.components.ItemGrid
import com.m4ykey.ui.components.PlaylistCard
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedPlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel = hiltViewModel(),
    onNavigateBack : () -> Unit,
    onPlaylistClick : (String) -> Unit = {}
) {

    var lazyPagingItems : Flow<PagingData<PlaylistItems>>? by remember { mutableStateOf(null) }
    var playlistList : LazyPagingItems<PlaylistItems>? by remember { mutableStateOf(null) }

    LaunchedEffect(viewModel) {
        val flow = viewModel.getPagingFeaturedPlaylist()
        lazyPagingItems = flow
    }

    playlistList = lazyPagingItems?.collectAsLazyPagingItems()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        color = if (isSystemInDarkTheme) Color.White else Color.Black,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        text = stringResource(id = R.string.featured_playlists)
                    ) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = if (isSystemInDarkTheme) Color.White else Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        ItemGrid(
            modifier = modifier.padding(paddingValues),
            itemList = playlistList,
            onItemClick = { clickedPlaylist ->
                onPlaylistClick(clickedPlaylist.id)
            }
        ) { playlist, _ ->
            PlaylistCard(
                playlist = playlist,
                onPlaylistClick = onPlaylistClick,
                size = 110.dp
            )
        }
    }
}
package com.m4ykey.ui.spotify.album

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.core.composable.NoInternetScreen
import com.m4ykey.core.network.NetworkViewModel
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.ui.R
import com.m4ykey.ui.components.AlbumCard
import com.m4ykey.ui.components.ItemGrid
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewReleaseScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onAlbumClick: (String) -> Unit,
    viewModel: AlbumViewModel = hiltViewModel()
) {

    val networkViewModel : NetworkViewModel = hiltViewModel()
    val isInternetAvailable by networkViewModel.isInternetAvailable.collectAsState()

    var lazyPagingItems : Flow<PagingData<Items>>? by remember { mutableStateOf(null) }
    var albumList : LazyPagingItems<Items>? by remember { mutableStateOf(null) }

    LaunchedEffect(viewModel) {
        val flow = viewModel.getPagingNewReleases()
        lazyPagingItems = flow
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    albumList = lazyPagingItems?.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        color = if (isSystemInDarkTheme) Color.White else Color.Black,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        text = stringResource(id = R.string.new_release)
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
        if (!isInternetAvailable) {
            NoInternetScreen(modifier = modifier.padding(paddingValues))
        } else {
            ItemGrid(
                modifier = modifier.padding(paddingValues),
                itemList = albumList,
                onItemClick = { clickedAlbum ->
                    onAlbumClick(clickedAlbum.id)
                }
            ) { album, _ ->
                AlbumCard(
                    item = album,
                    size = 110.dp,
                    onAlbumClick = onAlbumClick
                )
            }
        }
    }
}
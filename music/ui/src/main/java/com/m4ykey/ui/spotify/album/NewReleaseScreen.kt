package com.m4ykey.ui.spotify.album

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.ui.R
import com.m4ykey.ui.components.AlbumCard
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewReleaseScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onAlbumClick: (String) -> Unit,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    
    val context = LocalContext.current
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
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .padding(paddingValues),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                count = albumList?.itemCount ?: 0,
                key = albumList?.itemKey { it.id }
            ) { index ->
                val albums = albumList!![index]
                if (albums != null) {
                    AlbumCard(
                        size = 110.dp,
                        item = albums,
                        onAlbumClick = onAlbumClick
                    )
                }
            }

            item {
                if (albumList?.loadState?.append is LoadState.Error &&
                    (albumList?.loadState?.append as? LoadState.Error)?.endOfPaginationReached == true
                ) {
                    Log.i("EndOfPaginationReached", "NewReleaseScreen: End of pagination reached")
                } else {
                    when (val appendState = albumList?.loadState?.append) {
                        is LoadState.Loading -> { LoadingMaxWidth() }
                        is LoadState.Error -> { showToast(context, "Error $appendState") }
                        is LoadState.NotLoading -> Unit
                        else -> Unit
                    }
                }
            }
            item {
                when (val refreshState = albumList?.loadState?.refresh) {
                    is LoadState.Loading -> { LoadingMaxWidth() }
                    is LoadState.Error -> { showToast(context, "Error $refreshState") }
                    is LoadState.NotLoading -> Unit
                    else -> Unit
                }
            }
        }
    }
}
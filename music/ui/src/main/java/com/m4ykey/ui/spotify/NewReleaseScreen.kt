package com.m4ykey.ui.spotify

import androidx.compose.foundation.clickable
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
import androidx.paging.compose.itemContentType
import com.m4ykey.core.composable.LoadingMaxSize
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast
import com.m4ykey.ui.R
import com.m4ykey.ui.components.AlbumCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewReleaseScreen(
    modifier : Modifier = Modifier,
    onNavigateBack : () -> Unit,
    viewModel : AlbumViewModel = hiltViewModel(),
    onAlbumClick : (String) -> Unit
) {
    
    val context = LocalContext.current
    val lazyPagingItems = viewModel.observePagingFlow()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        if (lazyPagingItems.loadState.refresh is LoadState.Error) {
            showToast(context, "${lazyPagingItems.loadState.refresh as LoadState.Error}")
        }
    }

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
                count = lazyPagingItems.itemCount,
                key = { index -> lazyPagingItems[index]?.id.hashCode() },
                contentType = lazyPagingItems.itemContentType { "albumType" }
            ) { index ->
                val albums = lazyPagingItems[index]
                if (albums != null) {
                    AlbumCard(
                        size = 110.dp,
                        item = albums,
                        modifier = modifier.clickable { onAlbumClick(albums.id) }
                    )
                }
            }

            when (lazyPagingItems.loadState.append) {
                LoadState.Loading -> {
                    item { LoadingMaxWidth() }
                }
                is LoadState.Error -> {
                    showToast(context, "${lazyPagingItems.loadState.append as LoadState.Error}")
                }
                is LoadState.NotLoading -> Unit
            }

            when (lazyPagingItems.loadState.refresh) {
                LoadState.Loading -> {
                    item { LoadingMaxSize() }
                }
                is LoadState.Error -> {
                    showToast(context, "${lazyPagingItems.loadState.refresh as LoadState.Error}")
                }
                is LoadState.NotLoading -> Unit
            }
        }
    }
}
package com.m4ykey.ui.spotify

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.ui.R
import com.m4ykey.ui.components.AlbumCard
import com.m4ykey.ui.components.LoadingMaxSize
import com.m4ykey.ui.components.LoadingMaxWidth

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

    LaunchedEffect(Unit) {
        if (lazyPagingItems.loadState.refresh is LoadState.Error) {
            Toast.makeText(context, "${lazyPagingItems.loadState.refresh as LoadState.Error}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.new_release)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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
                key = lazyPagingItems.itemKey { it.id },
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
                is LoadState.Error -> {}
                is LoadState.NotLoading -> Unit
            }

            when (lazyPagingItems.loadState.refresh) {
                LoadState.Loading -> {
                    item { LoadingMaxSize() }
                }
                is LoadState.Error -> {}
                is LoadState.NotLoading -> Unit
            }
        }
    }
}
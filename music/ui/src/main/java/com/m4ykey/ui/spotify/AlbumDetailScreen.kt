package com.m4ykey.ui.spotify

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.LoadingMaxSize
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.composable.StyledText
import com.m4ykey.ui.R
import com.m4ykey.ui.components.TrackItemList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    modifier: Modifier = Modifier,
    id: String,
    viewModel: AlbumViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getAlbumById(id)
    }

    val state by viewModel.albumDetailUiState.collectAsState()
    val albumState = state.albumDetail
    val image = albumState?.images?.maxByOrNull { it.height * it.width }
    val imageUrl = image?.url
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val artistList = albumState?.artists?.joinToString(", ") { it.name }
    val infoStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.poppins))
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                title = { },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = if (isSystemInDarkTheme) Color.White else Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                LoadingMaxSize()
            }

            state.error != null -> {

            }

            else -> {
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoadImage(
                        url = imageUrl,
                        modifier = modifier
                            .clip(RoundedCornerShape(10))
                            .size(220.dp),
                        contentDescription = "Album Cover - ${albumState?.name}"
                    )
                    Spacer(modifier = modifier.height(20.dp))
                    StyledText(
                        text = albumState?.name ?: "",
                        fontSize = 23.sp,
                        modifier = modifier.fillMaxWidth(),
                        color = if (isSystemInDarkTheme) Color.White else Color.Black,
                        maxLines = 5,
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                    )
                    StyledText(
                        text = artistList ?: "",
                        fontSize = 16.sp,
                        modifier = modifier.fillMaxWidth(),
                        color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                        maxLines = 5,
                        fontFamily = FontFamily(Font(R.font.poppins))
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = albumState?.albumType?.replaceFirstChar { it.uppercase() } ?: "",
                            style = infoStyle
                        )
                        Text(
                            style = infoStyle,
                            text = " • ${albumState?.releaseDate ?: ""} • "
                        )
                        Text(
                            style = infoStyle,
                            text = "${albumState?.totalTracks ?: 0} " + stringResource(id = R.string.tracks)
                        )
                    }
                    Spacer(modifier = modifier.height(10.dp))
                    TrackList(
                        modifier = modifier.wrapContentHeight(),
                        albumId = albumState?.id ?: ""
                    )
                }
            }
        }
    }
}

@Composable
fun TrackList(
    modifier: Modifier = Modifier,
    albumId: String,
    viewModel: AlbumViewModel = hiltViewModel()
) {

    val lazyPagingItems = viewModel.observePagingTrackList(albumId = albumId)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (lazyPagingItems.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "${lazyPagingItems.loadState.refresh as LoadState.Error}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = lazyPagingItems.itemCount,
            contentType = lazyPagingItems.itemContentType { "trackType" },
            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            val tracks = lazyPagingItems[index]
            if (tracks != null) {
                TrackItemList(track = tracks)
            }
        }

        when (lazyPagingItems.loadState.append) {
            LoadState.Loading -> {
                item { LoadingMaxWidth() }
            }

            is LoadState.Error -> {
                Toast.makeText(
                    context,
                    "Error ${lazyPagingItems.loadState.append as LoadState.Error}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is LoadState.NotLoading -> Unit
        }

        when (lazyPagingItems.loadState.refresh) {
            LoadState.Loading -> {
                item { LoadingMaxSize() }
            }

            is LoadState.Error -> {
                Toast.makeText(
                    context,
                    "Error ${lazyPagingItems.loadState.refresh as LoadState.Error}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is LoadState.NotLoading -> Unit
        }
    }
}
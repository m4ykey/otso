package com.m4ykey.ui.spotify

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.itemContentType
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.LoadingMaxSize
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.composable.StyledText
import com.m4ykey.core.urls.openUrl
import com.m4ykey.core.urls.shareUrl
import com.m4ykey.ui.R
import com.m4ykey.ui.components.TrackItemList
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    modifier: Modifier = Modifier,
    id: String,
    viewModel: AlbumViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    navigateToArtist : (String) -> Unit = {}
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
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

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
                },
                actions = {
                    IconButton(onClick = { isSheetOpen = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.more),
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
                Toast.makeText(context, "${state.error}", Toast.LENGTH_SHORT).show()
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
                            .size(230.dp),
                        contentDescription = "Album Cover - ${albumState?.name}"
                    )
                    Spacer(modifier = modifier.height(20.dp))
                    StyledText(
                        text = artistList ?: "",
                        fontSize = 15.sp,
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { navigateToArtist(albumState?.artists?.get(0)?.id ?: "") },
                        color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                        maxLines = 5,
                        fontFamily = FontFamily(Font(R.font.poppins))
                    )
                    StyledText(
                        text = albumState?.name ?: "",
                        fontSize = 23.sp,
                        modifier = modifier.fillMaxWidth(),
                        color = if (isSystemInDarkTheme) Color.White else Color.Black,
                        maxLines = 5,
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
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
                            text = " • ${shortFormatReleaseDate(albumState?.releaseDate ?: "")}"
                        )
                        Text(
                            modifier = modifier.weight(1f),
                            style = infoStyle,
                            text = " • ${albumState?.totalTracks} " + stringResource(id = R.string.tracks)
                        )
                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
                    }
                    Spacer(modifier = modifier.height(10.dp))
                    TrackList(
                        modifier = modifier.wrapContentHeight(),
                        albumId = albumState?.id ?: ""
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Box(modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        formatReleaseDate(albumState?.releaseDate ?: "")?.let { Text(text = it) }
                    }
                }
            }
        }
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = false },
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LoadImage(
                        url = imageUrl,
                        modifier = modifier
                            .clip(RoundedCornerShape(10))
                            .size(50.dp)
                    )
                    Spacer(modifier = modifier.width(10.dp))
                    Text(
                        text = albumState?.name ?: "",
                        fontFamily = FontFamily(Font(R.font.poppins))
                    )
                }
                HorizontalDivider(
                    modifier = modifier.padding(10.dp)
                )
                BottomSheetItems(
                    title = stringResource(id = R.string.open_on_spotify),
                    onItemClick = {
                        openUrl(
                            context = context,
                            url = albumState?.externalUrls?.spotify ?: ""
                        )
                    },
                    icon = Icons.Outlined.Album
                )
                BottomSheetItems(
                    title = stringResource(id = R.string.share_album),
                    onItemClick = {
                        shareUrl(
                            context = context,
                            albumState?.externalUrls?.spotify ?: ""
                        )
                    },
                    icon = Icons.Outlined.Share
                )
                BottomSheetItems(
                    title = stringResource(id = R.string.show_artist),
                    onItemClick = {
                        navigateToArtist(albumState?.artists?.get(0)?.id ?: "")
                    },
                    icon = painterResource(id = R.drawable.ic_artist)
                )
            }
        }
    }
}

@Composable
fun BottomSheetItems(
    modifier: Modifier = Modifier,
    title: String,
    onItemClick: () -> Unit,
    icon: Any
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onItemClick() }
    ) {
        when (icon) {
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = modifier.size(26.dp)
                )
            }
            is Painter -> {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = modifier.size(26.dp)
                )
            }
        }
        Spacer(modifier = modifier.width(20.dp))
        Text(
            text = title,
            fontFamily = FontFamily(Font(R.font.poppins)),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
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
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = lazyPagingItems.itemCount,
            contentType = lazyPagingItems.itemContentType { "trackType" },
            key = { index -> lazyPagingItems[index]?.id.hashCode() }
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
                Log.i("TrackListError", "${lazyPagingItems.loadState.append as LoadState.Error}")
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
                item { LoadingMaxWidth() }
            }

            is LoadState.Error -> {
                Log.i("TrackListError", "${lazyPagingItems.loadState.refresh as LoadState.Error}")
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

fun shortFormatReleaseDate(releaseDate: String?): String? {
    return releaseDate?.takeIf { it.isNotEmpty() }?.let {
        try {
            val parsedDate = LocalDate.parse(it)
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.getDefault())
            outputFormatter.format(parsedDate)
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
        }
    }?.toString()
}

fun formatReleaseDate(releaseDate: String?): String? {
    return releaseDate?.takeIf { it.isNotEmpty() }?.let {
        try {
            val parsedDate = LocalDate.parse(it)
            val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
            outputFormatter.format(parsedDate)
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
        }
    }?.toString()
}
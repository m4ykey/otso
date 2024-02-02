package com.m4ykey.ui.spotify.album

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.core.composable.BottomSheetItems
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.LoadingMaxSize
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.composable.StyledText
import com.m4ykey.core.helpers.showToast
import com.m4ykey.core.urls.openUrl
import com.m4ykey.core.urls.shareUrl
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.ui.R
import com.m4ykey.ui.components.TrackItemList
import kotlinx.coroutines.flow.Flow
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
    //onTrackClick : (String, String) -> Unit
) {

    var lazyPagingItems: Flow<PagingData<TrackItem>>? by remember { mutableStateOf(null) }
    var trackList : LazyPagingItems<TrackItem>? by remember { mutableStateOf(null) }

    LaunchedEffect(viewModel) {
        viewModel.getAlbumById(id)
        lazyPagingItems = viewModel.getPagingTrackList(albumId = id)
    }

    trackList = lazyPagingItems?.collectAsLazyPagingItems()

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val infoStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.poppins)),
        color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }

    val albumState by viewModel.albumDetailUiState.collectAsState()
    val albumDetail = albumState.albumDetail

    val imageUrl = albumDetail?.images?.maxByOrNull { it.width!! * it.height!! }?.url
    val artistList = albumDetail?.artists?.joinToString(", ") { it.name }

    val albumType = when {
        albumDetail?.totalTracks in 2..6 && albumDetail?.albumType.equals("Single", ignoreCase = true) -> "EP"
        else -> albumDetail?.albumType
    }

    var isAlbumLiked by remember { mutableStateOf(false) }

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
            albumState.isLoading -> { LoadingMaxSize() }

            albumState.error != null -> { showToast(context, "${albumState.error}") }

            else -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoadImage(
                        url = imageUrl,
                        modifier = modifier
                            .clip(RoundedCornerShape(10))
                            .size(230.dp),
                        contentDescription = "Album Cover - ${albumDetail?.name}"
                    )
                    Spacer(modifier = modifier.height(20.dp))
                    StyledText(
                        text = artistList.orEmpty(),
                        fontSize = 15.sp,
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { },
                        color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                        maxLines = 5,
                        fontFamily = FontFamily(Font(R.font.poppins))
                    )
                    StyledText(
                        text = albumDetail?.name.orEmpty(),
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
                            text = albumType?.replaceFirstChar { it.uppercase() }.toString(),
                            style = infoStyle
                        )
                        Text(
                            style = infoStyle,
                            text = " • ${shortFormatReleaseDate(albumDetail?.releaseDate.orEmpty())}"
                        )
                        Text(
                            modifier = modifier.weight(1f),
                            style = infoStyle,
                            text = " • ${albumDetail?.totalTracks} " + stringResource(id = R.string.tracks)
                        )
                        val icon = if (isAlbumLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                        Icon(
                            modifier = modifier.clickable { isAlbumLiked = !isAlbumLiked },
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray
                        )
                    }
                    Column(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        for (index in 0 until (trackList?.itemCount ?: 0)) {
                            val tracks = trackList!![index]
                            if (tracks != null) {
                                TrackItemList(track = tracks)
                            }
                        }

                        if (trackList?.loadState?.append is LoadState.Error &&
                            (trackList?.loadState?.append as? LoadState.Error)?.endOfPaginationReached == true
                        ) {
                            Log.i("EndOfPaginationReached", "NewReleaseScreen: End of pagination reached")
                        } else {
                            when (val appendState = trackList?.loadState?.append) {
                                is LoadState.Loading -> { LoadingMaxWidth() }
                                is LoadState.Error -> { showToast(context, "Error $appendState") }
                                is LoadState.NotLoading -> Unit
                                else -> Unit
                            }
                        }
                        when (val refreshState = trackList?.loadState?.refresh) {
                            is LoadState.Loading -> { LoadingMaxWidth() }
                            is LoadState.Error -> { showToast(context, "Error $refreshState") }
                            is LoadState.NotLoading -> Unit
                            else -> Unit
                        }
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
                        .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 5.dp)
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
                        text = albumDetail?.name.orEmpty(),
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                            url = albumDetail?.externalUrls?.spotify.orEmpty()
                        )
                    },
                    icon = Icons.Outlined.Album,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )
                BottomSheetItems(
                    title = stringResource(id = R.string.share_album),
                    onItemClick = {
                        shareUrl(
                            context = context,
                            url = albumDetail?.externalUrls?.spotify.orEmpty()
                        )
                    },
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    icon = Icons.Outlined.Share
                )
                BottomSheetItems(
                    title = stringResource(id = R.string.show_artist),
                    onItemClick = { },
                    icon = painterResource(id = R.drawable.ic_artist),
                    fontFamily = FontFamily(Font(R.font.poppins))
                )
            }
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
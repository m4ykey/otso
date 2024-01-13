package com.m4ykey.ui.spotify

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
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.LoadingMaxSize
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.composable.StyledText
import com.m4ykey.core.helpers.showToast
import com.m4ykey.core.urls.openUrl
import com.m4ykey.core.urls.shareUrl
import com.m4ykey.ui.R
import com.m4ykey.ui.components.TrackItemList
import com.m4ykey.ui.spotify.uistate.CombinedAlbumState
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

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val infoStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.poppins)),
        color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
    )
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    val combinedState = observeAlbumDetails(albumId = id, viewModel = viewModel)
    val albumState = combinedState.albumDetailState?.albumDetail
    val trackListState = combinedState.lazyPagingItems

    val imageUrl = albumState?.images?.maxByOrNull { it.width * it.height }?.url
    val artistList = albumState?.artists?.joinToString(", ") { it.name }

    val albumType = when {
        albumState?.totalTracks in 3..5 && albumState?.albumType.equals("Single", ignoreCase = true) -> "EP"
        else -> albumState?.albumType
    }

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
            combinedState.albumDetailState?.isLoading == true -> { LoadingMaxSize() }

            combinedState.albumDetailState?.error != null -> { showToast(context, "${combinedState.albumDetailState.error}") }

            else -> {
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .verticalScroll(scrollState),
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
                        text = artistList.toString(),
                        fontSize = 15.sp,
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { navigateToArtist(albumState?.artists!![0].id) },
                        color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                        maxLines = 5,
                        fontFamily = FontFamily(Font(R.font.poppins))
                    )
                    StyledText(
                        text = albumState?.name.toString(),
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
//                        Text(
//                            style = infoStyle,
//                            text = " • $formattedDate"
//                        )
                        Text(
                            modifier = modifier.weight(1f),
                            style = infoStyle,
                            text = " • ${albumState?.totalTracks} " + stringResource(id = R.string.tracks)
                        )
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                        )
                    }
                    Spacer(modifier = modifier.height(10.dp))
                    Column(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        for (index in 0 until trackListState.itemCount) {
                            val tracks = trackListState[index]
                            if (tracks != null) {
                                TrackItemList(track = tracks)
                            }
                        }

                        when (val appendState = trackListState.loadState.append) {
                            is LoadState.Loading -> { LoadingMaxWidth() }
                            is LoadState.Error -> { showToast(context, "Error $appendState") }
                            is LoadState.NotLoading -> Unit
                        }
                        when (val refreshState = trackListState.loadState.refresh) {
                            is LoadState.Loading -> { LoadingMaxWidth() }
                            is LoadState.Error -> { showToast(context, "Error $refreshState") }
                            is LoadState.NotLoading -> Unit
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
                        text = albumState?.name !!,
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
                            url = albumState?.externalUrls?.spotify ?: ""
                        )
                    },
                    icon = Icons.Outlined.Share
                )
                BottomSheetItems(
                    title = stringResource(id = R.string.show_artist),
                    onItemClick = {
                        navigateToArtist(albumState?.artists!![0].id)
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

    val isSystemInDarkTheme = isSystemInDarkTheme()

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
                    modifier = modifier.size(26.dp),
                    tint = if (isSystemInDarkTheme) Color.White else Color.Black
                )
            }
            is Painter -> {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = modifier.size(26.dp),
                    tint = if (isSystemInDarkTheme) Color.White else Color.Black
                )
            }
        }
        Spacer(modifier = modifier.width(20.dp))
        Text(
            text = title,
            fontFamily = FontFamily(Font(R.font.poppins)),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = if (isSystemInDarkTheme) Color.White else Color.Black
        )
    }
}

@Composable
fun observeAlbumDetails(albumId : String, viewModel: AlbumViewModel = hiltViewModel()) : CombinedAlbumState {
    val albumState by viewModel.albumDetailUiState.collectAsState()
    val lazyItems = viewModel.observePagingTrackList(albumId = albumId)
    return CombinedAlbumState(albumState, lazyItems)
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
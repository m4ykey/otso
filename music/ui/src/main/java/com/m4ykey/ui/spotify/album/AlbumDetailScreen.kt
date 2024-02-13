package com.m4ykey.ui.spotify.album

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.LoadingMaxSize
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.composable.NoInternetScreen
import com.m4ykey.core.helpers.showToast
import com.m4ykey.core.network.NetworkViewModel
import com.m4ykey.core.urls.openUrl
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.ui.R
import com.m4ykey.ui.components.ErrorScreen
import com.m4ykey.ui.components.TrackList
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
    onTrackClick: (String, String, String) -> Unit,
    onArtistClick : (String) -> Unit
) {

    val networkViewModel : NetworkViewModel = hiltViewModel()
    val isInternetAvailable by networkViewModel.isInternetAvailable.collectAsState()

    var lazyPagingItems: Flow<PagingData<TrackItem>>? by remember { mutableStateOf(null) }
    var trackList: LazyPagingItems<TrackItem>? by remember { mutableStateOf(null) }

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

    val albumState by viewModel.albumDetailUiState.collectAsState()
    val albumDetail = albumState.albumDetail

    val imageUrl = albumDetail?.images?.maxByOrNull { it.width!! * it.height!! }?.url
    val artistList = albumDetail?.artists?.joinToString(", ") { it.name }
    val imageId = imageUrl?.substringAfterLast("/")

    val albumType = when {
        albumDetail?.totalTracks in 2..6 && albumDetail?.albumType.equals(
            "Single",
            ignoreCase = true
        ) -> "EP"
        else -> albumDetail?.albumType?.replaceFirstChar { it.uppercase() }
    }

    val totalTracks = "${albumDetail?.totalTracks} " + stringResource(id = R.string.tracks)
    val releaseDate = shortFormatReleaseDate(albumDetail?.releaseDate.orEmpty())

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
            albumState.isLoading -> LoadingMaxSize()
            albumState.error != null -> ErrorScreen(error = albumState.error!!)
            else -> {
                if (!isInternetAvailable) {
                    NoInternetScreen(modifier = modifier.padding(paddingValues))
                } else {
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
                                .size(280.dp),
                            contentDescription = "Album Cover - ${albumDetail?.name}"
                        )
                        Spacer(modifier = modifier.height(20.dp))
                        Text(
                            text = albumDetail?.name.orEmpty(),
                            fontSize = 23.sp,
                            modifier = modifier.fillMaxWidth(),
                            color = if (isSystemInDarkTheme) Color.White else Color.Black,
                            fontFamily = FontFamily(Font(R.font.poppins_medium))
                        )
                        Text(
                            text = artistList.orEmpty(),
                            fontSize = 15.sp,
                            modifier = modifier.align(Alignment.Start),
                            color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                            fontFamily = FontFamily(Font(R.font.poppins))
                        )
                        Row(
                            modifier = modifier
                                .padding(top = 2.dp, bottom = 2.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            AlbumButtons(
                                modifier = modifier.weight(1f),
                                navigation = { onArtistClick(albumDetail?.artists!![0].id) },
                                text = stringResource(id = R.string.artist)
                            )
                            AlbumButtons(
                                modifier = modifier.weight(1f),
                                navigation = { openUrl(context = context, url = albumDetail?.externalUrls?.spotify.orEmpty()) },
                                text = "Album"
                            )
                        }
                        Spacer(modifier = modifier.height(5.dp))
                        AlbumIcons(
                            modifier = modifier
                                .align(Alignment.Start)
                                .padding(bottom = 5.dp)
                        )
                        Text(
                            style = infoStyle,
                            text = "$albumType • $releaseDate • $totalTracks",
                            modifier = modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = modifier.height(10.dp))
                        Column(
                            modifier = modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            for (index in 0 until (trackList?.itemCount ?: 0)) {
                                val tracks = trackList!![index]
                                if (tracks != null) {
                                    TrackList(
                                        track = tracks,
                                        onTrackClick = onTrackClick,
                                        image = imageId!!
                                    )
                                }
                            }

                            if (trackList?.loadState?.append is LoadState.Error &&
                                (trackList?.loadState?.append as? LoadState.Error)?.endOfPaginationReached == true
                            ) {
                                Log.i(
                                    "EndOfPaginationReached",
                                    "NewReleaseScreen: End of pagination reached"
                                )
                            } else {
                                when (val appendState = trackList?.loadState?.append) {
                                    is LoadState.Loading -> LoadingMaxWidth()
                                    is LoadState.Error -> showToast(context, "Error $appendState")
                                    is LoadState.NotLoading -> Unit
                                    else -> Unit
                                }
                            }
                            when (val refreshState = trackList?.loadState?.refresh) {
                                is LoadState.Loading -> LoadingMaxWidth()
                                is LoadState.Error -> showToast(context, "Error $refreshState")
                                is LoadState.NotLoading -> Unit
                                else -> Unit
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlbumButtons(
    modifier: Modifier = Modifier,
    navigation: () -> Unit,
    text: String
) {
    Button(
        onClick = { navigation() },
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
            color = Color.White
        )
    }
}

@Composable
fun AlbumIcons(modifier : Modifier = Modifier) {
    var isAlbumClicked by remember { mutableStateOf(false) }
    var isListenLaterClicked by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isAlbumClicked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = stringResource(id = R.string.save_album),
            modifier = modifier.clickable { isAlbumClicked = !isAlbumClicked },
            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        Icon(
            imageVector = if (isListenLaterClicked) Icons.Default.AccessTimeFilled else Icons.Default.AccessTime, 
            contentDescription = stringResource(id = R.string.listen_later_album),
            modifier = modifier.clickable { isListenLaterClicked = !isListenLaterClicked },
            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
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
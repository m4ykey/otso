package com.m4ykey.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.m4ykey.core.Constants.DEEZER_LOGO
import com.m4ykey.core.Constants.SHAZAM_LOGO
import com.m4ykey.core.Constants.SOUNDCLOUD_LOGO
import com.m4ykey.core.Constants.SPOTIFY_LOGO
import com.m4ykey.core.Constants.TIDAL_LOGO
import com.m4ykey.core.Constants.YT_MUSIC_LOGO
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.notification.MusicNotificationState
import com.m4ykey.core.notification.checkNotificationListenerPermission
import com.m4ykey.ui.spotify.album.NewReleaseHome
import com.m4ykey.ui.spotify.playlist.FeaturedPlaylistHome
import com.m4ykey.ui.video.TrendingVideosHome
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicHomeScreen(
    modifier: Modifier = Modifier,
    onNewReleaseClick: () -> Unit,
    onAlbumClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onFeaturedPlaylistClick : () -> Unit,
    onPlaylistClick : (String) -> Unit = {},
    viewModel: MusicHomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentTime = LocalTime.now()
    val greeting = when {
        currentTime.isAfter(LocalTime.of(6, 0)) && currentTime.isBefore(LocalTime.of(18, 0)) -> context.getString(R.string.hello)
        else -> context.getString(R.string.good_evening)
    }

    val titleStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.generalsans_medium)),
        fontSize = 20.sp,
        color = if (isSystemInDarkTheme()) Color.White else Color.Black
    )

    val artist by MusicNotificationState.artist.collectAsState()
    val title by MusicNotificationState.title.collectAsState()
    val appInfo by MusicNotificationState.app.collectAsState()
    val subText by MusicNotificationState.subText.collectAsState()

    val isNotificationAccessGranted by viewModel.isNotificationAccessGranted.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = greeting,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(end = 10.dp, start = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.currently_playing),
                style = titleStyle,
                modifier = modifier.padding(5.dp)
            )
            if (isNotificationAccessGranted == true) {
                if (!title.isNullOrEmpty() && !artist.isNullOrEmpty()) {
                    DisplayCurrentlyPlaying(title.orEmpty(), artist.orEmpty(), subText.orEmpty(), appInfo.orEmpty())
                } else {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.onSecondary)
                    ) {
                        Text(
                            text = stringResource(id = R.string.nothing_is_currently_playing),
                            modifier = modifier
                                .padding(bottom = 10.dp, top = 10.dp)
                                .fillMaxWidth(),
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                }
            } else {
                viewModel.checkNotificationAccess(context)
                CheckPermission(modifier, context)
            }
            Spacer(modifier = modifier.height(5.dp))
            Searchbar(navigation = onSearchClick)
            Spacer(modifier = modifier.height(5.dp))
            NavigationArrow(
                navigation = { onNewReleaseClick() },
                text = stringResource(id = R.string.latest_new_releases),
                style = titleStyle
            )
            NewReleaseHome(onAlbumClick = onAlbumClick)
            Spacer(modifier = modifier.height(10.dp))
            Text(
                modifier = modifier.padding(5.dp),
                text = stringResource(id = R.string.most_popular_videos),
                style = titleStyle
            )
            TrendingVideosHome()
            Spacer(modifier = modifier.height(10.dp))
            NavigationArrow(
                navigation = { onFeaturedPlaylistClick() },
                text = stringResource(id = R.string.featured_playlists),
                style = titleStyle
            )
            FeaturedPlaylistHome(onPlaylistClick = onPlaylistClick)
        }
    }
}

@Composable
fun Searchbar(
    modifier : Modifier = Modifier,
    navigation: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100))
            .background(MaterialTheme.colorScheme.onSecondary)
            .clickable { navigation() }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = modifier.padding(5.dp),
                tint = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Spacer(modifier = modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.what_would_like_to_search),
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
    }
}

@Composable
fun DisplayCurrentlyPlaying(
    title : String,
    artist : String,
    subText : String,
    appInfo : String
) {
    val logos = mapOf(
        "com.spotify.music" to SPOTIFY_LOGO,
        "com.google.android.apps.youtube.music" to YT_MUSIC_LOGO,
        "deezer.package.name" to DEEZER_LOGO,
        "com.soundcloud.android" to SOUNDCLOUD_LOGO,
        "com.aspiro.tidal" to TIDAL_LOGO,
        "com.shazam.android" to SHAZAM_LOGO
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        Text(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp),
            text = title,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium))
        )
        Text(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
            text = artist,
            color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.poppins))
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, bottom = 5.dp, end = 5.dp)
        ) {
            LoadImage(
                url = logos[appInfo],
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = subText,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppins))
            )
        }
    }
}

@Composable
private fun CheckPermission(
    modifier: Modifier = Modifier,
    context: Context
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.onSecondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.notification_access),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.generalsans_medium))
        )
        Button(onClick = {
            checkNotificationListenerPermission(context)
        }) {
            Text(text = stringResource(id = R.string.allow_access))
        }
    }
}

@Composable
fun NavigationArrow(
    modifier: Modifier = Modifier,
    navigation: () -> Unit,
    text : String,
    style : TextStyle
) {
    Row(
        modifier = modifier
            .clickable { navigation() }
            .padding(5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = modifier.weight(1f),
            style = style
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}

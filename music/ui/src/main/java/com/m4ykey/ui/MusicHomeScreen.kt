package com.m4ykey.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.m4ykey.core.notification.MusicNotificationState
import com.m4ykey.core.notification.checkNotificationListenerPermission
import com.m4ykey.ui.spotify.album.NewReleaseHome
import com.m4ykey.ui.video.TrendingVideosHome
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicHomeScreen(
    modifier: Modifier = Modifier,
    onNewReleaseClick: () -> Unit,
    onAlbumClick: (String) -> Unit,
    onSearchClick: () -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val currentTime = LocalTime.now()
    val greeting = when {
        currentTime.isAfter(LocalTime.of(6, 0)) && currentTime.isBefore(LocalTime.of(18, 0)) -> context.getString(R.string.hello)
        else -> context.getString(R.string.good_evening)
    }
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val titleStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.generalsans_medium)),
        fontSize = 20.sp,
        color = if (isSystemInDarkTheme) Color.White else Color.Black
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val artist by MusicNotificationState.artist.collectAsState()
    val title by MusicNotificationState.title.collectAsState()

    val isNotificationAccessGranted by viewModel.isNotificationAccessGranted.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                title = {
                    Text(
                        text = greeting,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = if (isSystemInDarkTheme) Color.White else Color.Black
                    )
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { onSearchClick() }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search),
                            tint = if (isSystemInDarkTheme) Color.White else Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(end = 10.dp, start = 10.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.currently_playing),
                style = titleStyle,
                modifier = modifier.padding(5.dp)
            )
            if (isNotificationAccessGranted == true) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(10))
                        .background(MaterialTheme.colorScheme.onSecondary)
                ) {
                    if (!title.isNullOrEmpty() && !artist.isNullOrEmpty()) {
                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Column(
                                //modifier = modifier.fillMaxHeight()
                            ) {
                                Text(text = title.toString())
                                Text(text = artist.toString())
                            }
                        }
                    } else {
                        Text(text = stringResource(id = R.string.nothing_is_currently_playing))
                    }
                }
            } else {
                viewModel.checkNotificationAccess(context)
                CheckPermission(modifier, context)
            }
            Text(
                modifier = modifier.padding(5.dp),
                text = stringResource(id = R.string.latest_new_releases),
                style = titleStyle
            )
            NewReleaseHome(
                modifier = modifier,
                onNewReleaseClick = onNewReleaseClick,
                onAlbumClick = onAlbumClick
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                modifier = modifier.padding(5.dp),
                text = stringResource(id = R.string.most_popular_videos),
                style = titleStyle
            )
            TrendingVideosHome(modifier = modifier)
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
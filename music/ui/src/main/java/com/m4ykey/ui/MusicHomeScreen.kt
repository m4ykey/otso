package com.m4ykey.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.ui.spotify.NewReleaseHome
import com.m4ykey.ui.video.TrendingVideosHome
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicHomeScreen(
    modifier: Modifier = Modifier,
    onNewReleaseClick: () -> Unit,
    onAlbumClick : (String) -> Unit,
    onSearchClick : () -> Unit = {}
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = greeting,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = if (isSystemInDarkTheme) Color.White else Color.Black
                    )
                },
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
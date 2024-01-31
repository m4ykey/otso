package com.m4ykey.ui.lyrics

import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.LoadingMaxSize
import com.m4ykey.core.helpers.showToast
import com.m4ykey.ui.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongScreen(
    modifier: Modifier = Modifier,
    viewModel: LyricsViewModel = hiltViewModel(),
    trackName: String,
    artistName: String,
    onNavigateBack: () -> Unit
) {

    LaunchedEffect(viewModel) {
        viewModel.searchLyrics("$trackName $artistName")
    }

    val lyricsState by viewModel.lyrics.collectAsState()
    val lyrics = lyricsState.result

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val videoId = getIdFromYoutubeUrl(lyrics?.media?.firstOrNull()?.url.toString())
    var isPlaying by remember { mutableStateOf(false) }

    when {
        lyricsState.isLoading -> {
            LoadingMaxSize()
        }

        lyricsState.error != null -> {
            showToast(context, "${lyricsState.error}")
        }

        else -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    LoadImage(url = lyrics?.headerImageUrl)
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart)
                            .padding(top = 5.dp)
                    ) {
                        IconButton(onClick = { onNavigateBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back),
                                tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getIdFromYoutubeUrl(videoId: String) : String? {
    val uri = Uri.parse(videoId)
    return uri.getQueryParameter("v")
}

@Composable
fun YoutubePlayer(
    modifier : Modifier = Modifier,
    lifecycleOwner: LifecycleOwner,
    videoId : String
) {
    AndroidView(
        modifier = modifier
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(10))
            .fillMaxWidth(),
        factory = { context ->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0.0f)
                    }
                })
            }
        }
    )
}
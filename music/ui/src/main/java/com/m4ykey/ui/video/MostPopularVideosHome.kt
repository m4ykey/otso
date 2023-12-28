package com.m4ykey.ui.video

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun TrendingVideosHome(
    modifier : Modifier = Modifier
) {
    val viewModel : VideoViewModel = hiltViewModel()
    val state by viewModel.videoUiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    when {
        state.isLoading -> {
            ConstraintLayout(
                modifier = modifier.fillMaxWidth()
            ) {
                val progressBar = createRef()
                CircularProgressIndicator(
                    modifier = modifier.constrainAs(progressBar) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                )
            }
        }
        state.error != null -> {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
        else -> {
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    state.videos,
                    key = { it.id }
                ) { video ->
                    VideoCard(
                        youtubeId = video.id,
                        lifecycleOwner = lifecycleOwner
                    )
                }
            }
        }
    }
}

@Composable
fun VideoCard(
    modifier : Modifier = Modifier,
    youtubeId : String,
    lifecycleOwner : LifecycleOwner
) {
    AndroidView(
        modifier = modifier
            .width(340.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(10)),
        factory = { context ->
        YouTubePlayerView(context = context).apply {
            lifecycleOwner.lifecycle.addObserver(this)
            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoId = youtubeId, startSeconds = 0.0f)
                }
            })
        }
    })
}
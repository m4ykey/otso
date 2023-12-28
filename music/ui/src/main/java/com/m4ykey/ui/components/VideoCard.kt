package com.m4ykey.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    youtubeId: String
) {

    val context = LocalContext.current
    val youtubePlayerView = remember { YouTubePlayerView(context) }

    AndroidView(
        modifier = modifier
            .width(320.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(10)),
        factory = { youtubePlayerView }
    )

    DisposableEffect(youtubePlayerView) {
        youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId = youtubeId, startSeconds = 0.0f)
            }
        })
        onDispose {  }
    }
}
package com.m4ykey.ui.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.urls.openUrl
import com.m4ykey.ui.components.ErrorScreen
import com.m4ykey.ui.components.ItemRowList
import com.m4ykey.ui.components.ThumbnailsCard

@Composable
fun TrendingVideosHome(
    viewModel : VideoViewModel = hiltViewModel()
) {
    val state by viewModel.videoUiState.collectAsState()
    val context = LocalContext.current

    when {
        state.isLoading -> LoadingMaxWidth()
        state.error != null -> ErrorScreen(error = state.error!!)
        state.videos.isNotEmpty() -> {
            ItemRowList(
                itemList = state.videos,
                onItemClick = {  }
            ) { video, _ ->
                ThumbnailsCard(
                    video = video,
                    openUrl = { openUrl(context, "https://www.youtube.com/watch?v=${video.id}") }
                )
            }
        }
        else -> {}
    }
}
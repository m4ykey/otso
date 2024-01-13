package com.m4ykey.ui.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast
import com.m4ykey.core.urls.openUrl
import com.m4ykey.ui.components.ThumbnailsCard

@Composable
fun TrendingVideosHome(
    modifier : Modifier = Modifier
) {
    val viewModel : VideoViewModel = hiltViewModel()
    val state by viewModel.videoUiState.collectAsState()
    val context = LocalContext.current

    when {
        state.isLoading -> {
            LoadingMaxWidth()
        }
        state.error != null -> {
            showToast(context, state.error!!)
        }
        else -> {
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    state.videos,
                    key = { it.id ?: "" }
                ) { video ->
                    ThumbnailsCard(
                        video = video,
                        openUrl = { id ->
                            openUrl(context, id)
                        }
                    )
                }
            }
        }
    }
}
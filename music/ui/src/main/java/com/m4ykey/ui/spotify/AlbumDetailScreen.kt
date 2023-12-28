package com.m4ykey.ui.spotify

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.helpers.LoadImage

@Composable
fun AlbumDetailScreen(
    modifier : Modifier = Modifier,
    id : String,
    viewModel: AlbumViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getAlbumById(id)
    }

    val state by viewModel.albumDetailUiState.collectAsState()
    val image = state.albumDetail?.images?.find { it.height == 640 && it.width == 640 }

    Column {
        LoadImage(
            url = image?.url,
            modifier = modifier.size(200.dp)
        )
    }

}
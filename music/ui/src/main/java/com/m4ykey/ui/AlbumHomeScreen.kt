package com.m4ykey.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.helpers.LoadImage
import com.m4ykey.data.domain.model.Items

@Composable
fun AlbumCard(
    modifier : Modifier = Modifier,
    item : Items
) {
    val image = item.images!!.find { it.height == 640 && it.width == 640 }
    Column(
        modifier = modifier
            .width(200.dp)
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Card(
            modifier = modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            LoadImage(
                modifier = modifier.height(200.dp),
                url = image?.toString()
            )
        }
        Text(
            text = item.name!!,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            modifier = modifier.padding(5.dp)
        )
    }
}

@Composable
fun NewReleaseHomeScreen(
    modifier : Modifier = Modifier
) {
    val viewModel : AlbumViewModel = hiltViewModel()
    val state by viewModel.albumUiState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.getNewReleases()
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.error != null -> {
                Text(text = state.error.toString())
            }
            else -> {
                LazyRow(modifier = modifier) {
                    items(state.albums) { album ->
                        AlbumCard(item = album)
                    }
                }
            }
        }
    }
}
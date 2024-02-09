package com.m4ykey.ui.lyrics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LyricsScreen(
    modifier : Modifier = Modifier,
    artist : String,
    track : String,
    viewModel: LyricsViewModel = hiltViewModel(),
    onNavigateBack : () -> Unit
) {

    LaunchedEffect(viewModel) {
        viewModel.searchLyrics(artist = artist, track = track)
    }

    val lyricsState by viewModel.lyrics.collectAsState()
    val lyrics = lyricsState.lyrics

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
            Column(modifier = modifier.fillMaxHeight()) {
                Text(text = track)
                Text(text = artist)
            }
        }
        Column(modifier = modifier.fillMaxSize()) {
            Text(
                text = lyrics?.lyricsBody.orEmpty(),
                modifier = modifier.verticalScroll(rememberScrollState())
            )
            Text(
                text = lyrics?.lyricsCopyright.orEmpty()
            )
        }
    }

}
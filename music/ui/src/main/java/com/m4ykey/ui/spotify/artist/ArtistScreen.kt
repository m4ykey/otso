package com.m4ykey.ui.spotify.artist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ArtistScreen(
    modifier : Modifier = Modifier,
    viewModel: ArtistViewModel = hiltViewModel(),
    onNavigateClick : () -> Unit,
    artistId : String
) {

    LaunchedEffect(viewModel) {
        viewModel.getArtistById(artistId)
    }

    val state by viewModel.artist.collectAsState()
    val artistState = state.artist

}
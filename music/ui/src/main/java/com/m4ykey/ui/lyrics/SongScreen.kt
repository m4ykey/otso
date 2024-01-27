package com.m4ykey.ui.lyrics

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongScreen(
    modifier : Modifier = Modifier,
    viewModel: LyricsViewModel = hiltViewModel(),
    trackName : String,
    artistName : String,
    onNavigateBack : () -> Unit
) {

    LaunchedEffect(viewModel) {
        viewModel.searchLyrics("$trackName $artistName")
    }

    val search by viewModel.search.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = trackName) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (search.result?.isNotEmpty() == true) {
                val title = search.result!![0].fullTitle
                Text(text = title)
            }
        }
    }

}
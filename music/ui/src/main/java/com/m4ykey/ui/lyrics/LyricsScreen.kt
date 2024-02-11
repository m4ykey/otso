package com.m4ykey.ui.lyrics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.m4ykey.ui.R

@Composable
fun LyricsScreen(
    modifier : Modifier = Modifier,
    artist : String,
    track : String,
    viewModel: LyricsViewModel = hiltViewModel(),
    onNavigateBack : () -> Unit,
    image : String
) {

    LaunchedEffect(viewModel) {
        viewModel.searchLyrics(artist = artist, track = track)
    }

    val lyricsState by viewModel.lyrics.collectAsState()
    val lyrics = lyricsState.lyrics
    val imageUrl = "https://i.scdn.co/image/$image"
    val correctedImageUrl = imageUrl.removeSuffix("}")

    val lyricsText = if (lyrics?.lyricsBody?.isEmpty() == true) {
        stringResource(id = R.string.no_lyrics)
    } else {
        lyrics?.lyricsBody.orEmpty()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(model = correctedImageUrl),
            contentDescription = null,
            modifier = modifier
                .fillMaxSize()
                .alpha(0.1f),
            contentScale = ContentScale.Crop
        )
    }
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color.White
                )
            }
            Text(
                text = track,
                modifier = modifier.fillMaxWidth(),
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
        }
        Column(
            modifier = modifier
                .padding(5.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = lyricsText,
                color = Color.White,
                fontSize = 17.sp,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = lyrics?.lyricsCopyright.orEmpty(),
                color = Color.White,
                fontSize = 13.sp,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.poppins))
            )
        }
    }
}
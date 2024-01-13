package com.m4ykey.ui.video

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.composable.LoadingMaxSize
import com.m4ykey.core.composable.StyledText
import com.m4ykey.core.helpers.formatDate
import com.m4ykey.ui.R
import com.m4ykey.ui.components.VideoPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDetailScreen(
    modifier : Modifier = Modifier,
    id : String,
    viewModel: VideoViewModel = hiltViewModel(),
    onNavigateBack : () -> Unit
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val state by viewModel.videoDetailUiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val video = state.videos.firstOrNull()
    val formatDate = formatDate(
        date = video?.snippet?.publishedAt,
        inputPattern = "yyyy-MM-ddTHH:mm:ssZ",
        outputPattern = "dd MMM yyyy"
    )
    Log.i("Video", "VideoDetailScreen: $video")

    LaunchedEffect(Unit) {
        viewModel.getVideoDetails(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = if (isSystemInDarkTheme) Color.White else Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                LoadingMaxSize()
            }

            state.error != null -> {
                Toast.makeText(context, "${state.error}", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VideoPlayer(videoId = id, lifecycleOwner = lifecycleOwner)
                    StyledText(
                        text = video?.snippet?.title ?: "",
                        fontSize = 20.sp,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        color = if (isSystemInDarkTheme) Color.White else Color.Black,
                        maxLines = 5,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        textAlign = TextAlign.Justify
                    )
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { }
                    ) {
                        Text(text = "${video?.statistics?.viewCount } ${context.getString(R.string.views)}")
                        Text(text = formatDate ?: "")
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticsColumn(
    modifier: Modifier = Modifier,
    title : String,
    icon : ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Text(text = title)
    }
}
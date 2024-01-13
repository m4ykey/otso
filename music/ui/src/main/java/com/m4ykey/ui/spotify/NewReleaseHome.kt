package com.m4ykey.ui.spotify

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast
import com.m4ykey.ui.R
import com.m4ykey.ui.components.AlbumCard

@Composable
fun NewReleaseHome(
    modifier: Modifier = Modifier,
    onNewReleaseClick: () -> Unit,
    onAlbumClick : (String) -> Unit
) {
    val viewModel: AlbumViewModel = hiltViewModel()
    val state by viewModel.albumUiState.collectAsState()
    val context = LocalContext.current
    val isSystemInDarkTheme = isSystemInDarkTheme()

    when {
        state.isLoading -> { LoadingMaxWidth() }
        state.error != null -> { showToast(context, state.error!!) }
        else -> {
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    state.albums,
                    key = { it.id }
                ) { album ->
                    AlbumCard(
                        item = album,
                        size = 120.dp,
                        modifier = modifier.clickable { onAlbumClick(album.id) }
                    )
                }
                item {
                    Column(
                        modifier = modifier
                            .width(120.dp)
                            .clickable { onNewReleaseClick() }
                    ) {
                        Card(
                            modifier = modifier.height(120.dp),
                            shape = RoundedCornerShape(10),
                            elevation = CardDefaults.cardElevation(0.dp),
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                            Box(
                                modifier = modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = modifier.size(32.dp),
                                    tint = if (isSystemInDarkTheme) Color.White else Color.Black
                                )
                            }
                        }
                        Text(
                            text = stringResource(id = R.string.see_more),
                            textAlign = TextAlign.Center,
                            modifier = modifier.fillMaxWidth(),
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = if (isSystemInDarkTheme) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}
package com.m4ykey.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.StyledText
import com.m4ykey.data.domain.model.playlist.PlaylistItems
import com.m4ykey.ui.R

@Composable
fun PlaylistCard(
    modifier : Modifier = Modifier,
    playlist : PlaylistItems,
    onPlaylistClick : (String) -> Unit,
    size : Dp
) {
    val playlistImage = playlist.images.firstOrNull()?.url

    Column(
        modifier = modifier
            .width(size)
            .clickable { onPlaylistClick(playlist.id) }
    ) {
        LoadImage(
            url = playlistImage,
            contentDescription = "Playlist cover - ${playlist.name}",
            modifier = modifier
                .size(size)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10))
        )
        StyledText(
            text = playlist.name,
            fontSize = 15.sp,
            modifier = modifier.fillMaxWidth(),
            color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
            maxLines = 2,
            fontFamily = FontFamily(Font(R.font.generalsans_medium))
        )
    }
}
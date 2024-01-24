package com.m4ykey.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.core.composable.StyledText
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.ui.R

@Composable
fun TrackItemList(
    modifier: Modifier = Modifier,
    track : TrackItem,
    onTrackClick : (String) -> Unit
) {
    val artists = track.artists.joinToString(", ") { it.name }
    val seconds = track.durationMs / 1000
    val trackDuration = String.format("%d:%02d", seconds / 60, seconds % 60)
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTrackClick(track.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(end = 5.dp)
        ) {
            StyledText(
                text = track.name,
                fontSize = 15.sp,
                modifier = modifier.fillMaxWidth(),
                color = if (isSystemInDarkTheme) Color.White else Color.Black,
                maxLines = 5,
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
            StyledText(
                text = artists,
                fontSize = 13.sp,
                modifier = modifier.fillMaxWidth(),
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                maxLines = 5,
                fontFamily = FontFamily(Font(R.font.poppins))
            )
        }
        Text(
            text = trackDuration,
            fontFamily = FontFamily(Font(R.font.poppins)),
            color = if (isSystemInDarkTheme) Color.White else Color.Black,
            fontSize = 13.sp
        )
    }
}
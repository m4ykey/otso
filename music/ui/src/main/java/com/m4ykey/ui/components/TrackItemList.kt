package com.m4ykey.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.ui.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackList(
    modifier : Modifier = Modifier,
    onTrackClick: (String, String) -> Unit,
    track: TrackItem
) {
    val artistInfo = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins)),
        fontSize = 13.sp,
        color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTrackClick(track.name, track.artists[0].name) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = modifier.weight(1f)) {
            Text(
                text = track.name,
                fontSize = 15.sp,
                modifier = modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (track.explicit) {
                    Explicit()
                }
                Text(
                    text = track.artists.joinToString(", ") { it.name },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = artistInfo,
                    modifier = Modifier.basicMarquee()
                )
            }
        }
        Text(
            text = formatDuration(track.durationMs / 1000),
            style = artistInfo,
            modifier = modifier.padding(start = 5.dp)
        )
        Spacer(modifier = modifier.width(5.dp))
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
            modifier = modifier.clickable {  }
        )
    }
}

@Composable
fun Explicit() {
    Box(
        modifier = Modifier
            .padding(end = 5.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(color = Color(0xFF525252))
    ) {
        Text(
            text = "E",
            style = TextStyle(
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                color = Color.White
            ),
            modifier = Modifier.padding(start = 5.dp, end = 5.dp)
        )
    }
}

fun formatDuration(seconds : Int) : String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}
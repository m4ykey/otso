package com.m4ykey.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.data.domain.model.video.VideoItem
import com.m4ykey.ui.R

@Composable
fun ThumbnailsCard(
    modifier: Modifier = Modifier,
    video : VideoItem
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = modifier.width(300.dp)
    ) {
        Card(
            modifier = modifier
                .height(150.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(10)
        ) {
            LoadImage(
                url = video.snippet.thumbnails.standard.url,
                contentDescription = "Thumbnails for ${video.snippet.title}"
            )
        }
        Text(
            text = video.snippet.title,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            color = if (isSystemInDarkTheme) Color.White else Color.Black
        )
    }
}
package com.m4ykey.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.ui.R

@Composable
fun AlbumCard(
    modifier : Modifier = Modifier,
    item : Items,
    size : Dp,
    onAlbumClick : (String) -> Unit
) {
    val image = item.images.maxByOrNull { it.width * it.height }?.url
    val artistList = item.artists.joinToString(", ") { it.name }
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = modifier
            .width(size)
            .clickable { onAlbumClick(item.id) }
    ) {
        LoadImage(
            url = image.toString(),
            contentDescription = "Album cover = ${item.name}",
            modifier = modifier
                .height(size)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10))
        )
        StyledText(
            text = item.name,
            fontSize = 15.sp,
            color = if (isSystemInDarkTheme) Color.White else Color.Black,
            maxLines = 2,
            fontFamily = FontFamily(Font(R.font.generalsans_medium)),
            modifier = modifier.fillMaxWidth()
        )
        StyledText(
            text = artistList,
            fontSize = 12.sp,
            color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
            maxLines = 1,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            modifier = modifier.fillMaxWidth()
        )
    }
}
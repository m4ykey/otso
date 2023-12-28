package com.m4ykey.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.core.helpers.LoadImage
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.ui.R

@Composable
fun AlbumCard(
    modifier : Modifier = Modifier,
    item : Items,
    size : Dp
) {
    val image = item.images.find { it.height == 640 && it.width == 640 }
    val artistList = item.artists.joinToString(", ") { it.name }
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = modifier.width(size)
    ) {
        Card(
            modifier = modifier.height(size),
            shape = RoundedCornerShape(10),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            LoadImage(url = image?.url.toString())
        }
        Text(
            text = item.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 15.sp,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.generalsans_medium)),
            color = if (isSystemInDarkTheme) Color.White else Color.Black
        )
        Text(
            color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.fillMaxWidth(),
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontSize = 12.sp,
            text = artistList
        )
    }
}
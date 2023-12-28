package com.m4ykey.ui.spotify

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AlbumDetailScreen(
    modifier : Modifier = Modifier,
    id : String
) {
    Text(text = "Album id: $id")
}
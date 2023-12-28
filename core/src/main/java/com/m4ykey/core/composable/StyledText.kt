package com.m4ykey.core.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun StyledText(
    text : String,
    fontSize : TextUnit,
    modifier : Modifier = Modifier,
    color : Color,
    maxLines : Int,
    fontFamily: FontFamily
) {
    Text(
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        fontSize = fontSize,
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontFamily = fontFamily,
        color = color
    )
}
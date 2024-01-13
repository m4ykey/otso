package com.m4ykey.core.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetItems(
    modifier: Modifier = Modifier,
    title: String,
    onItemClick: () -> Unit,
    icon: Any,
    fontFamily : FontFamily
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onItemClick() }
    ) {
        when (icon) {
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = modifier.size(24.dp),
                    tint = if (isSystemInDarkTheme) Color.White else Color.Black
                )
            }
            is Painter -> {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = modifier.size(24.dp),
                    tint = if (isSystemInDarkTheme) Color.White else Color.Black
                )
            }
        }
        Spacer(modifier = modifier.width(20.dp))
        StyledText(
            text = title,
            fontSize = 14.sp,
            modifier = modifier.fillMaxWidth(),
            color = if (isSystemInDarkTheme) Color.White else Color.Black,
            maxLines = 1,
            fontFamily = fontFamily
        )
    }
}
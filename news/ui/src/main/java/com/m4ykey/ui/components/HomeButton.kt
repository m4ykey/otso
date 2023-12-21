package com.m4ykey.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.ui.R

@Composable
fun HomeButton(
    modifier: Modifier = Modifier,
    navigation: () -> Unit,
    text: Int
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Button(
        onClick = { navigation() },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 5.dp),
        shape = CutCornerShape(10),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme) Color.DarkGray else Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = text),
                fontFamily = FontFamily(Font(R.font.generalsans_medium)),
                fontSize = 16.sp,
                color = if (isSystemInDarkTheme) Color.White else Color.Black
            )
            Spacer(modifier = modifier.width(5.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = if (isSystemInDarkTheme) Color.White else Color.Black,
                modifier = modifier.size(20.dp)
            )
        }
    }
}
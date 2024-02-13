package com.m4ykey.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.ui.R

@Composable
fun ErrorScreen(error : String) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.something_went_wrong),
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontSize = 15.sp
        )
        Text(
            text = error,
            fontSize = 12.sp,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            fontFamily = FontFamily(Font(R.font.poppins)),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ErrorScreenPrev() {
    ErrorScreen(error = "Something went wrong")
}
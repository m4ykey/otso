package com.m4ykey.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.ui.spotify.NewReleaseHome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicHomeScreen(
    modifier : Modifier = Modifier,
    onNewReleaseClick : () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(title = {  })
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(end = 10.dp, start = 10.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                modifier = modifier.padding(5.dp),
                text = stringResource(id = R.string.latest_new_releases),
                fontFamily = FontFamily(Font(R.font.generalsans_medium)),
                fontSize = 20.sp
            )
            NewReleaseHome(onNewReleaseClick = onNewReleaseClick)
        }
    }
}
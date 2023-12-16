package com.m4ykey.otso

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.m4ykey.ui.NewsHomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    onNewsClick : () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Home") })
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            NewsHomeScreen(onNewsClick = onNewsClick)
        }
    }

}
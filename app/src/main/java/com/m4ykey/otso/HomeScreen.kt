package com.m4ykey.otso

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.m4ykey.ui.NewReleaseHomeScreen
import com.m4ykey.ui.NewsHomeScreen
import com.m4ykey.ui.components.HomeButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNewsClick: () -> Unit,
    onSearchClick: () -> Unit = {},
    onNewReleaseClick : () -> Unit
) {

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.home)) },
                actions = {
                    IconButton(onClick = { onSearchClick() }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            HomeButton(
                navigation = { onNewsClick() },
                text = R.string.read_more
            )
            NewsHomeScreen()
            Spacer(modifier = modifier.height(10.dp))
            HomeButton(
                navigation = { onNewReleaseClick() },
                text = R.string.discover_more
            )
            NewReleaseHomeScreen()
        }
    }
}
package com.m4ykey.ui

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.helpers.OpenUrl
import com.m4ykey.ui.components.NewsHomeCard
import com.m4ykey.ui.helpers.DisposableEffectCallback

@Composable
fun NewsHomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel : NewsViewModel = hiltViewModel()
    val state by viewModel.newsUiState.collectAsState()
    val context = LocalContext.current
    val callback = rememberUpdatedState(DisposableEffectCallback())

    val openUrl = rememberLauncherForActivityResult(OpenUrl()) { result ->
        if (!result) {
            callback.value.onOpenUrlResult(context)
        }
    }

    DisposableEffect(callback) {
        callback.value.launcher = openUrl

        onDispose {
            callback.value.launcher = null
        }
    }

    if (state.isLoading) {
        CircularProgressIndicator()
    }
    LazyRow(modifier = modifier.fillMaxWidth()) {
        items(state.news) { article ->
            NewsHomeCard(
                article = article,
                onArticleClick = { url ->
                    openUrl.launch(url)
                }
            )
        }
    }

    DisposableEffect(state.error) {
        onDispose {
            state.error?.let { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
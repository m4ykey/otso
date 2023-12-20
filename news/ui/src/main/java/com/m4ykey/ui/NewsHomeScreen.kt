package com.m4ykey.ui

import android.net.ConnectivityManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.helpers.OpenUrl
import com.m4ykey.core.network.NetworkStateMonitor
import com.m4ykey.ui.components.HomeButton
import com.m4ykey.ui.components.NewsCard
import com.m4ykey.ui.helpers.DisposableEffectCallback
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewsHomeScreen(
    modifier : Modifier = Modifier,
    connectivityManager: ConnectivityManager,
    onNewsClick : () -> Unit
) {
    val viewModel : NewsViewModel = hiltViewModel()
    val state by viewModel.newsUiState.collectAsState()
    val context = LocalContext.current
    val callback = rememberUpdatedState(DisposableEffectCallback())
    val networkStateMonitor = remember(connectivityManager) {
        NetworkStateMonitor(connectivityManager)
    }

    DisposableEffect(Unit) {
        networkStateMonitor.startMonitoring()
        onDispose {
            networkStateMonitor.stopMonitoring()
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.getLatestNews(
            page = 1,
            pageSize = 3
        )
    }

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

    LaunchedEffect(viewModel.newsUiState) {
        snapshotFlow { viewModel.newsUiState }
            .collectLatest { state ->
                state.value.error?.let { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(state.news) { article ->
            NewsCard(
                article = article,
                onArticleClick = { url ->
                    openUrl.launch(url)
                }
            )
        }
        item {
            HomeButton(
                navigation = { onNewsClick() },
                text = R.string.see_more
            )
        }
    }
    if (state.isLoading) {
        CircularProgressIndicator()
    }
}
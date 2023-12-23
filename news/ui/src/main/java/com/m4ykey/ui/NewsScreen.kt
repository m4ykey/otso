package com.m4ykey.ui

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.core.helpers.OpenUrl
import com.m4ykey.ui.helpers.DisposableEffectCallback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: NewsViewModel = hiltViewModel()
    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
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

    LaunchedEffect(lazyPagingItems.loadState.refresh) {
        if (lazyPagingItems.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: ${(lazyPagingItems.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.news)) },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.url },
                contentType = lazyPagingItems.itemContentType { "contentType" }
            ) { index ->
                val article = lazyPagingItems[index]
                if (article != null) {
                    NewsListCard(
                        article = article,
                        onArticleClick = { url ->
                            openUrl.launch(url)
                        }
                    )
                }
            }
            item {
                if (lazyPagingItems.loadState.append is LoadState.Loading) {
                   // CircularProgressIndicator()
                }
            }
        }
        Box(modifier = modifier) {
            if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
//                CircularProgressIndicator(
//                    modifier = modifier.align(Alignment.Center)
//                )
            }
        }
    }
}
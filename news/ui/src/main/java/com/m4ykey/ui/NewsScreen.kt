package com.m4ykey.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast
import com.m4ykey.core.urls.openUrl
import com.m4ykey.ui.components.NewsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: NewsViewModel = hiltViewModel()
    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.news),
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = if (isSystemInDarkTheme) Color.White else Color.Black
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.url },
                contentType = lazyPagingItems.itemContentType { "articleType" }
            ) { index ->
                val article = lazyPagingItems[index]
                if (article != null) {
                    NewsCard(
                        article = article,
                        onArticleClick = { url ->
                            openUrl(context = context, url = url)
                        }
                    )
                }
            }

            item {
                when (val appendState = lazyPagingItems.loadState.append) {
                    is LoadState.Loading -> { LoadingMaxWidth() }
                    is LoadState.Error -> { showToast(context, "Error $appendState") }
                    is LoadState.NotLoading -> Unit
                }
            }
            item {
                when (val refreshState = lazyPagingItems.loadState.refresh) {
                    is LoadState.Loading -> { LoadingMaxWidth() }
                    is LoadState.Error -> { showToast(context, "Error $refreshState") }
                    is LoadState.NotLoading -> Unit
                }
            }
        }
    }
}
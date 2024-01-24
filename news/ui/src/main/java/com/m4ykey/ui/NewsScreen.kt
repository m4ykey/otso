package com.m4ykey.ui

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast
import com.m4ykey.core.urls.openUrl
import com.m4ykey.data.domain.model.Article
import com.m4ykey.ui.components.NewsCard
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    var lazyPagingItems : Flow<PagingData<Article>>? by remember { mutableStateOf(null) }
    var newsList : LazyPagingItems<Article>? by remember { mutableStateOf(null) }

    LaunchedEffect(viewModel) {
        val flow = viewModel.getPagingNews()
        lazyPagingItems = flow
    }

    newsList = lazyPagingItems?.collectAsLazyPagingItems()

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
                count = newsList?.itemCount ?: 0,
                key = newsList?.itemKey { it.url }
            ) { index ->
                val article = newsList!![index]
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
                if (newsList?.loadState?.append is LoadState.Error &&
                    (newsList?.loadState?.append as? LoadState.Error)?.endOfPaginationReached == true
                ) {
                    Log.i("EndOfPaginationReached", "NewsScreen: End of pagination reached")
                } else {
                    when (val appendState = newsList?.loadState?.append) {
                        is LoadState.Loading -> { LoadingMaxWidth() }
                        is LoadState.Error -> { showToast(context, "Error $appendState") }
                        is LoadState.NotLoading -> Unit
                        else -> Unit
                    }
                }
            }
            item {
                when (val refreshState = newsList?.loadState?.refresh) {
                    is LoadState.Loading -> { LoadingMaxWidth() }
                    is LoadState.Error -> { showToast(context, "Error $refreshState") }
                    is LoadState.NotLoading -> Unit
                    else -> Unit
                }
            }
        }
    }
}
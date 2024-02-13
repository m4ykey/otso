package com.m4ykey.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.core.composable.NoInternetScreen
import com.m4ykey.core.network.NetworkViewModel
import com.m4ykey.core.urls.openUrl
import com.m4ykey.data.domain.model.Article
import com.m4ykey.ui.components.ItemColumnList
import com.m4ykey.ui.components.NewsCard
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {

    val networkViewModel : NetworkViewModel = hiltViewModel()
    val isInternetAvailable by networkViewModel.isInternetAvailable.collectAsState()

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
        if (!isInternetAvailable) {
            NoInternetScreen()
        } else {
            ItemColumnList(
                modifier = modifier.padding(paddingValues),
                onItemClick = {  },
                itemList = newsList
            ) { article, _ ->
                NewsCard(
                    article = article,
                    onArticleClick = { openUrl(context, url = article.url) }
                )
            }
        }
    }
}
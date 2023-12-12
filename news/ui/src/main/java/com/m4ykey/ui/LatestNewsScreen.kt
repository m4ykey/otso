package com.m4ykey.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.data.remote.model.Article
import com.m4ykey.ui.helpers.LoadImage
import com.m4ykey.ui.helpers.formatPublishedDate

@Composable
fun LatestNewsCard(
    modifier : Modifier = Modifier,
    article: Article
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .width(350.dp)
    ) {
        Card(
            modifier = modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            LoadImage(url = article.urlToImage)
        }
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = article.title ?: stringResource(id = R.string.no_title),
            modifier = modifier.fillMaxWidth(),
            fontSize = 19.sp,
            fontFamily = FontFamily(Font(R.font.generalsans_medium)),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Spacer(modifier = modifier.height(5.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = article.source.name ?: stringResource(id = R.string.no_source_name),
                modifier = modifier.weight(1f),
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.poppins))
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                fontFamily = FontFamily(Font(R.font.poppins)),
                text = formatPublishedDate(article.publishedAt ?: stringResource(id = R.string.no_date)),
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun LatestNews(
    modifier : Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.latestNewsUiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getLatestNews()
    }

    if (state.error != null) {
        NewsErrorScreen()
    } else if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxHeight()
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyRow(modifier = modifier) {
            items(state.news.size) { n ->
                val news = state.news[n]
                LatestNewsCard(article = news)
            }
        }
    }
}
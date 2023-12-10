package com.m4ykey.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.m4ykey.data.remote.model.Article

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {

    val state = viewModel.newsUiState

    if (state.error != null) {
        Text(text = state.error.toString())
        Log.i("NewsScreen", "NewsScreen: ${state.error}")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(state.news.size) { n ->
                val news = state.news[n]
                if (n >= state.news.size - 1 && !state.endReached && !state.isLoading) {
                    LaunchedEffect(viewModel) { viewModel.getNextNews() }
                }
                ArticleCard(article = news)
            }
            item {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ArticleCard(
    modifier : Modifier = Modifier,
    article: Article
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .build(),
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = article.source.name ?: "No source name")
        Text(text = article.title ?: "No title")
        Text(text = article.publishedAt ?: "No date")
    }
}
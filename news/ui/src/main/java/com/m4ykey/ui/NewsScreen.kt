package com.m4ykey.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.m4ykey.data.remote.model.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    onNavigateBack : () -> Unit
) {

    val state = viewModel.newsUiState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "News") })
        }
    ) {
        if (state.error != null) {
            Text(text = state.error.toString())
        } else {
            LazyColumn(
                modifier = modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
        Text(text = state.error.toString())
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
                ArticleCard(article = news)
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
            .padding(10.dp)
            .width(350.dp)
    ) {
        Card(
            modifier = modifier
                .height(220.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage ?: "")
                    .build(),
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = article.title ?: "",
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
                text = article.source.name ?: "",
                modifier = modifier.weight(1f),
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.poppins))
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                fontFamily = FontFamily(Font(R.font.poppins)),
                text = formatPublishedDate(article.publishedAt ?: "no date"),
                fontSize = 15.sp
            )
        }
    }
}

private fun formatPublishedDate(date : String) : String {
    val inputDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputDate = DateTimeFormatter.ofPattern("dd MMM yyyy")

    val parsedDate = LocalDateTime.parse(date, inputDate)
    return outputDate.format(parsedDate)
}
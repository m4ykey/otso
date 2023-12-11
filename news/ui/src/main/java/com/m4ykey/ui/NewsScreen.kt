package com.m4ykey.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun NewsScreen(
    modifier : Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {

    val state = viewModel.newsUiState

    if (state.error != null) {
        Text(text = state.error.toString())
    } else {
        LazyRow(modifier = modifier) {
            items(state.news.size) { n ->
                val news = state.news[n]
                if (n >= state.news.size - 1 && !state.endReached && !state.isLoading) {
                    LaunchedEffect(viewModel) { viewModel.getNextNews() }
                }
                ArticleCard(article = news)
            }
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
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
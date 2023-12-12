package com.m4ykey.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.data.remote.model.Article
import com.m4ykey.ui.helpers.LoadImage
import com.m4ykey.ui.helpers.formatPublishedDate

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
                modifier = modifier.padding(it)
            ) {
                items(state.news.size) { n ->
                    val news = state.news[n]
                    if (n >= state.news.size - 1 && !state.endReached && !state.isLoading) {
                        LaunchedEffect(viewModel) { viewModel.getNextNews() }
                    }
                    NewsCard(article = news)
                }
                item {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: Article
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
    ) {
        Card(
            modifier = modifier.size(100.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            LoadImage(url = article.urlToImage)
        }
        Column(
            modifier = modifier
                .weight(1f)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = article.title ?: "",
                modifier = modifier.fillMaxWidth(),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.generalsans_medium)),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = article.description ?: "",
                modifier = modifier.fillMaxWidth(),
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontFamily = FontFamily(Font(R.font.poppins))
            )
            Spacer(modifier = modifier.height(5.dp))
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = article.source.name ?: "",
                    modifier = modifier.weight(1f),
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )
                Spacer(modifier = modifier.width(10.dp))
                Text(
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    text = formatPublishedDate(article.publishedAt ?: "no date"),
                    fontSize = 13.sp
                )
            }
        }
    }
}


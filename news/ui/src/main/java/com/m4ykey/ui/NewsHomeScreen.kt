package com.m4ykey.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.data.domain.model.Article
import com.m4ykey.ui.helpers.LoadImage

@Composable
fun NewsHomeScreen(
    modifier : Modifier = Modifier,
    onNewsClick : () -> Unit
) {
    val viewModel : NewsViewModel = hiltViewModel()
    val state by viewModel.newsUiState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.getLatestNews()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.error != null -> {
                Text(text = state.error.toString())
            }
            else -> {
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    LazyRow(modifier = modifier) {
                        items(state.news) { article ->
                            NewsHomeCard(article = article)
                        }
                        item {
                            Button(onClick = { onNewsClick() }) {
                                Text(text = "Click")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsHomeCard(
    modifier : Modifier = Modifier,
    article : Article
) {
    Card(
        modifier = modifier
            .padding(start = 5.dp, end = 5.dp)
            .width(300.dp)
            .height(190.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            LoadImage(url = article.urlToImage)
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = article.title)
            }
        }
    }
}
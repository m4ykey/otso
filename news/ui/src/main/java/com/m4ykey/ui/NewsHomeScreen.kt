package com.m4ykey.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m4ykey.core.helpers.LoadImage
import com.m4ykey.core.helpers.OpenUrl
import com.m4ykey.data.domain.model.Article
import com.m4ykey.ui.helpers.DisposableEffectCallback
import com.m4ykey.ui.helpers.formatPublishedDate

@Composable
fun NewsHomeScreen(
    modifier : Modifier = Modifier,
    onNewsClick : () -> Unit
) {
    val viewModel : NewsViewModel = hiltViewModel()
    val state by viewModel.newsUiState.collectAsState()
    val context = LocalContext.current
    val callback = rememberUpdatedState(DisposableEffectCallback())

    LaunchedEffect(viewModel) {
        viewModel.getLatestNews(
            page = 1,
            pageSize = 3
        )
    }

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

    LazyColumn(modifier = modifier) {
        items(state.news) { article ->
            NewsCard(
                article = article,
                onArticleClick = { url ->
                    openUrl.launch(url)
                }
            )
        }
        item {
            Button(onClick = onNewsClick) {
                Text(text = "See more")
            }
        }
    }
    Box(modifier = modifier) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        }
        if (state.error != null) {
            Text(text = state.error.toString())
        }
    }
}

@Composable
fun NewsCard(
    modifier : Modifier = Modifier,
    article : Article,
    onArticleClick : (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .fillMaxWidth()
            .clickable { onArticleClick(article.url) }
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(190.dp)
        ) {
            LoadImage(url = article.urlToImage)
        }
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = article.title,
            modifier = modifier.fillMaxWidth(),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.generalsans_medium)),
            overflow = TextOverflow.Ellipsis,
            maxLines = 3
        )
        Spacer(modifier = modifier.height(5.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "by ${article.source.name}",
                modifier = modifier.weight(1f),
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.poppins))
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                fontFamily = FontFamily(Font(R.font.poppins)),
                text = formatPublishedDate(
                    article.publishedAt
                ),
                fontSize = 13.sp
            )
        }
    }
}
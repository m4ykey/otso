package com.m4ykey.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.core.helpers.LoadImage
import com.m4ykey.data.domain.model.Article
import com.m4ykey.ui.R
import com.m4ykey.ui.helpers.formatPublishedDate

@Composable
fun NewsHomeCard(
    modifier : Modifier = Modifier,
    article : Article,
    onArticleClick : (String) -> Unit
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = modifier
            .width(350.dp)
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
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
            maxLines = 3,
            color = if (isSystemInDarkTheme) Color.White else Color.Black
        )
        Spacer(modifier = modifier.height(5.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = article.source.name,
                modifier = modifier.weight(1f),
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                fontFamily = FontFamily(Font(R.font.poppins)),
                text = formatPublishedDate(
                    article.publishedAt
                ),
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun NewsListCard(
    modifier : Modifier = Modifier,
    article : Article,
    onArticleClick : (String) -> Unit
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
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
            maxLines = 3,
            color = if (isSystemInDarkTheme) Color.White else Color.Black
        )
        Spacer(modifier = modifier.height(5.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = article.source.name,
                modifier = modifier.weight(1f),
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                fontFamily = FontFamily(Font(R.font.poppins)),
                text = formatPublishedDate(
                    article.publishedAt
                ),
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                fontSize = 13.sp
            )
        }
    }
}
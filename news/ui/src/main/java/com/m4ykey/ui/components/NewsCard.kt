package com.m4ykey.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.core.Constants.BILLBOARD_LOGO
import com.m4ykey.core.Constants.PITCHFORK_LOGO
import com.m4ykey.core.Constants.ROLLING_STONE_LOGO
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.StyledText
import com.m4ykey.data.domain.model.Article
import com.m4ykey.ui.R
import com.m4ykey.ui.helpers.formatPublishedDate

@Composable
fun NewsCard(
    modifier : Modifier = Modifier,
    article : Article,
    onArticleClick : (String) -> Unit
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()

    val logos = mapOf(
        "Rolling Stone" to ROLLING_STONE_LOGO,
        "Pitchfork" to PITCHFORK_LOGO,
        "Billboard" to BILLBOARD_LOGO
    )

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
            LoadImage(
                url = article.urlToImage,
                contentDescription = "Article Image - ${article.title}"
            )
        }
        Spacer(modifier = modifier.height(5.dp))
        StyledText(
            text = article.title,
            fontSize = 16.sp,
            color = if (isSystemInDarkTheme) Color.White else Color.Black,
            maxLines = 3,
            fontFamily = FontFamily(Font(R.font.generalsans_medium)),
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(5.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            LoadImage(
                url = logos[article.source.name],
                modifier = modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(10))
            )
            Spacer(modifier = modifier.width(5.dp))
            StyledText(
                text = article.source.name,
                fontSize = 13.sp,
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                maxLines = 3,
                fontFamily = FontFamily(Font(R.font.poppins)),
                modifier = modifier.weight(1f),
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                fontFamily = FontFamily(Font(R.font.poppins)),
                text = formatPublishedDate(article.publishedAt),
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                fontSize = 13.sp
            )
        }
    }
}
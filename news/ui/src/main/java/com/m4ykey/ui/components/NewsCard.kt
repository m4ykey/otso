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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.core.Constants.BILLBOARD_LOGO
import com.m4ykey.core.Constants.PITCHFORK_LOGO
import com.m4ykey.core.Constants.ROLLING_STONE_LOGO
import com.m4ykey.core.composable.LoadImage
import com.m4ykey.core.composable.StyledText
import com.m4ykey.core.helpers.formatDate
import com.m4ykey.core.urls.shareUrl
import com.m4ykey.data.domain.model.Article
import com.m4ykey.ui.R

@Composable
fun NewsCard(
    modifier : Modifier = Modifier,
    article : Article,
    onArticleClick : (String) -> Unit
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current

    val logos = mapOf(
        "Rolling Stone" to ROLLING_STONE_LOGO,
        "Pitchfork" to PITCHFORK_LOGO,
        "Billboard" to BILLBOARD_LOGO
    )

    val formattedDate = formatDate(
        date = article.publishedAt,
        inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
        outputPattern = "dd MMM yyyy"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
            .clickable { onArticleClick(article.url) }
    ) {
        LoadImage(
            url = article.urlToImage,
            contentDescription = "Article Image - ${article.title}",
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(10))
        )
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
                    .size(20.dp)
                    .clip(RoundedCornerShape(10))
            )
            Spacer(modifier = modifier.width(5.dp))
            StyledText(
                text = "${article.source.name} • ",
                fontSize = 12.sp,
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                maxLines = 3,
                fontFamily = FontFamily(Font(R.font.poppins)),
                modifier = modifier
            )
            Text(
                fontFamily = FontFamily(Font(R.font.poppins)),
                text = formattedDate ?: "",
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                fontSize = 12.sp,
                modifier = modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                modifier = modifier
                    .size(20.dp)
                    .clickable {
                        shareUrl(
                            context = context,
                            url = article.url
                        )
                    },
                tint = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray
            )
        }
    }
}
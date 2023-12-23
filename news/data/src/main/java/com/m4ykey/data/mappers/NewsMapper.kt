package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.Article
import com.m4ykey.data.domain.model.Source
import com.m4ykey.data.remote.model.ArticleDto

fun ArticleDto.toArticle() : Article {
    return Article(
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt ?: "",
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: "",
        author = author ?: "",
        source = Source(name = source.name ?: "")
    )
}
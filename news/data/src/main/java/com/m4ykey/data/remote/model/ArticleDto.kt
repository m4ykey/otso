package com.m4ykey.data.remote.model

data class ArticleDto(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: SourceDto,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)
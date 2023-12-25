package com.m4ykey.data.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)

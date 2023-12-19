package com.m4ykey.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_home_entity")
data class ArticleHomeEntity(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    @PrimaryKey(autoGenerate = false) val url: String,
    val urlToImage: String
)

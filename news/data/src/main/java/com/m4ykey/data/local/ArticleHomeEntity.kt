package com.m4ykey.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.m4ykey.core.Constants.ARTICLE_HOME_TABLE

@Entity(tableName = ARTICLE_HOME_TABLE)
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

package com.m4ykey.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ArticleDao {

    @Upsert
    suspend fun insertAll(articles : List<ArticleEntity>)

    @Query("DELETE FROM article_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM article_table")
    fun getAllArticles() : List<ArticleEntity>

}
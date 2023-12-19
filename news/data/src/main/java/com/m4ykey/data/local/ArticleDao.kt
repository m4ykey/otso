package com.m4ykey.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles : List<ArticleEntity>)

    @Query("DELETE FROM article_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM article_table")
    fun getAllArticles() : List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHome(articles : List<ArticleHomeEntity>)

    @Query("DELETE FROM article_home_entity")
    suspend fun deleteAllHome()

    @Query("SELECT * FROM article_home_entity")
    fun getAllHomeArticles() : List<ArticleHomeEntity>

}
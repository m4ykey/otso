package com.m4ykey.data.remote.repository

import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.remote.model.Article
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getNews(page: Int, pageSize: Int): Result<List<Article>> {
        return try {
            val news = newsApi.getNews(page = page, pageSize = pageSize).articles
            Result.success(news)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}
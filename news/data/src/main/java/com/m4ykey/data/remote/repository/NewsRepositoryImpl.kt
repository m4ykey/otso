package com.m4ykey.data.remote.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.remote.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun getLatestNews(page : Int, pageSize : Int): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val news = newsApi.getNews(
                    page = page,
                    pageSize = pageSize
                ).articles
                emit(Resource.Success(news))
            } catch (e : Exception) {
                emit(Resource.Error(
                    data = null,
                    message = e.localizedMessage ?: "An unexpected error occurred"
                ))
            }
        }
    }
}
package com.m4ykey.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.Article
import com.m4ykey.data.domain.repository.NewsRepository
import com.m4ykey.data.local.ArticleEntity
import com.m4ykey.data.local.NewsDatabase
import com.m4ykey.data.mappers.toArticle
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.remote.paging.ArticlePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api : NewsApi,
    private val db : NewsDatabase
) : NewsRepository {

    override fun getNewsPager(): Pager<Int, ArticleEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                ArticlePagingSource(db = db, api = api)
            }
        )
    }

    override suspend fun getLatestNews(): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val news = api.getNews(
                    page = 1,
                    pageSize = 10
                ).articles.map { it.toArticle() }
                emit(Resource.Success(news))
            } catch (e : Exception) {
                emit(Resource.Error(
                    data = null,
                    message = e.localizedMessage ?: "An unexpected error occurred: ${e.message}"
                ))
            }
        }
    }
}
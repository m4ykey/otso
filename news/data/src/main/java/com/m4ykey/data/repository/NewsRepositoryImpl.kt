package com.m4ykey.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.m4ykey.core.Constants.NEWS_PAGE_SIZE
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.Article
import com.m4ykey.data.domain.repository.NewsRepository
import com.m4ykey.data.local.ArticleEntity
import com.m4ykey.data.local.NewsDatabase
import com.m4ykey.data.mappers.toArticle
import com.m4ykey.data.mappers.toArticleHomeEntity
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.remote.paging.ArticlePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api : NewsApi,
    private val db : NewsDatabase
) : NewsRepository {

    override fun getNewsPager(): Pager<Int, ArticleEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = NEWS_PAGE_SIZE,
                maxSize = NEWS_PAGE_SIZE + (NEWS_PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ArticlePagingSource(db = db, api = api)
            }
        )
    }

    override suspend fun getLatestNews(page: Int, pageSize: Int): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading())

            val cachedNews = withContext(Dispatchers.IO) {
                db.dao.getAllHomeArticles().map { it.toArticle() }
            }

            emit(Resource.Loading(data = cachedNews))

            try {
                val networkNews = api.getNews(page = page, pageSize = pageSize).articles.map { it.toArticle() }
                val limitedNews = networkNews.take(3)

                withContext(Dispatchers.IO) {
                    db.dao.deleteAllHome()
                    db.dao.insertAllHome(limitedNews.map { it.toArticleHomeEntity() })
                }

                emit(Resource.Success(limitedNews))
            } catch (e: Exception) {
                emit(Resource.Error(
                    data = withContext(Dispatchers.IO) {
                        val limitedNews = db.dao.getAllHomeArticles()
                        limitedNews.take(3).map { it.toArticle() }
                    },
                    message = e.localizedMessage ?: "An unexpected error occurred: ${e.message}"
                ))
            }
        }
    }
}
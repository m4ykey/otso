package com.m4ykey.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.m4ykey.data.local.ArticleEntity
import com.m4ykey.data.local.NewsDatabase
import com.m4ykey.data.mappers.toArticleEntity
import com.m4ykey.data.remote.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ArticlePagingSource(
    private val api: NewsApi,
    private val db: NewsDatabase
) : PagingSource<Int, ArticleEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        return withContext(Dispatchers.IO) {
            try {
                val page = params.key ?: 1

                // Fetch data from the database
                val articleData = db.dao.getAllArticles()

                if (articleData.isNotEmpty()) {
                    // Data is available in the database
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (articleData.size == params.loadSize) page + 1 else null

                    LoadResult.Page(
                        data = articleData,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                } else {
                    // Data is not in the database, get from the API
                    val response = api.getNews(page = page, pageSize = params.loadSize)
                    val articles = response.articles.map { it.toArticleEntity() }

                    // Insert fetched data into the database
                    db.withTransaction {
                        db.dao.insertAll(articles)
                    }

                    // Calculate prevKey and nextKey
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (articles.isNotEmpty()) page + 1 else null

                    LoadResult.Page(
                        data = articles,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            } catch (e: IOException) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}
package com.m4ykey.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m4ykey.data.domain.model.Article
import com.m4ykey.data.mappers.toArticle
import com.m4ykey.data.remote.NewsApi
import retrofit2.HttpException

class ArticlePagingSource(
    private val api: NewsApi
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val response = api.getNews(
                page = page,
                pageSize = pageSize
            ).articles.map { it.toArticle() }

            LoadResult.Page(
                data = response,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (response.isNotEmpty()) page + 1 else null
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }
    }
}
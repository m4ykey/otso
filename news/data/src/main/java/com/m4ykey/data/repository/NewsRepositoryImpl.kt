package com.m4ykey.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.m4ykey.data.domain.repository.NewsRepository
import com.m4ykey.data.local.ArticleEntity
import com.m4ykey.data.local.NewsDatabase
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.remote.paging.ArticlePagingSource
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
}
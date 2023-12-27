package com.m4ykey.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.m4ykey.core.Constants.PAGE_SIZE
import com.m4ykey.data.domain.model.Article
import com.m4ykey.data.domain.repository.NewsRepository
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.remote.paging.ArticlePagingSource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    override fun getNewsPager(): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ArticlePagingSource(api = api)
            }
        )
    }
}
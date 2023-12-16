package com.m4ykey.data.domain.repository

import androidx.paging.Pager
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.Article
import com.m4ykey.data.local.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNewsPager(): Pager<Int, ArticleEntity>
    suspend fun getLatestNews() : Flow<Resource<List<Article>>>

}
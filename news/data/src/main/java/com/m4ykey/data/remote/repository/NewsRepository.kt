package com.m4ykey.data.remote.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.remote.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(page : Int, pageSize : Int) : Result<List<Article>>
    suspend fun getLatestNews(page : Int, pageSize: Int) : Flow<Resource<List<Article>>>

}
package com.m4ykey.data.remote.repository

import com.m4ykey.data.remote.model.Article

interface NewsRepository {

    suspend fun getNews(page : Int, pageSize : Int) : Result<List<Article>>

}
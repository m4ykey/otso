package com.m4ykey.data.remote

import com.m4ykey.core.Constants
import com.m4ykey.data.remote.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query : String = "music",
        @Query("pageSize") pageSize : Int,
        @Query("page") page : Int,
        @Query("sortBy") sortBy : String = "publishedAt",
        @Query("domains") sources : String = Constants.DOMAINS
    ) : News

}
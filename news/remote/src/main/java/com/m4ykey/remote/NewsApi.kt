package com.m4ykey.remote

import com.m4ykey.remote.data.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query : String = "music",
        @Query("pageSize") pageSize : Int = 10,
        @Query("page") page : Int = 1,
        @Query("sortBy") sortBy : String = "publishedAt"
    ) : News

}
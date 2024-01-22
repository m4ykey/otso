package com.m4ykey.data.remote.api

import com.m4ykey.core.Constants.PAGE_SIZE
import com.m4ykey.data.BuildConfig.YOUTUBE_API_KEY
import com.m4ykey.data.remote.model.youtube.TrendingVideoListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("videos")
    suspend fun getMostPopularVideos(
        @Query("part") part : String = "snippet",
        @Query("chart") chart : String = "mostPopular",
        @Query("videoCategoryId") category : Int = 10,
        @Query("maxResults") results : Int = PAGE_SIZE,
        @Query("key") key : String = YOUTUBE_API_KEY
    ) : TrendingVideoListDto

}
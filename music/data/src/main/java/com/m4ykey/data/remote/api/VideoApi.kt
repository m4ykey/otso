package com.m4ykey.data.remote.api

import com.m4ykey.core.Constants.PAGE_SIZE
import com.m4ykey.core.Constants.YOUTUBE_VIDEO_DETAIL_PART
import com.m4ykey.core.Keys
import com.m4ykey.data.remote.model.youtube.TrendingVideoListDto
import com.m4ykey.data.remote.model.youtube.VideoDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApi {

    @GET("videos")
    suspend fun getMostPopularVideos(
        @Query("part") part : String = "snippet",
        @Query("chart") chart : String = "mostPopular",
        @Query("videoCategoryId") category : Int = 10,
        @Query("maxResults") results : Int = PAGE_SIZE,
        @Query("key") key : String = Keys.YOUTUBE_API_KEY
    ) : TrendingVideoListDto

    @GET("videos/{id}")
    suspend fun getVideoDetails(
        @Path("id") videoId : String,
        @Query("part") part : String = YOUTUBE_VIDEO_DETAIL_PART,
        @Query("key") key : String = Keys.YOUTUBE_API_KEY
    ) : VideoDetailDto

}
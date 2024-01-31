package com.m4ykey.data.remote.api.music

import com.m4ykey.data.remote.model.track.SearchTrackDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TrackApi {

    @GET("search")
    suspend fun getTrackImage(
        @Query("q") query : String,
        @Header("Authorization") token : String
    ) : SearchTrackDto

}
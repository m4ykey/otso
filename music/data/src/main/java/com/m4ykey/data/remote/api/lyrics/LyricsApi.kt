package com.m4ykey.data.remote.api.lyrics

import com.m4ykey.data.remote.model.lyrics.LyricsListDto
import com.m4ykey.data.remote.model.lyrics.SongsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface LyricsApi {

    @GET("search")
    suspend fun searchLyrics(
        @Header("Authorization") auth : String,
        @Query("q") query : String
    ) : LyricsListDto

    @GET("songs/{id}")
    suspend fun getLyrics(
        @Path("id") id: Int,
        @Header("Authorization") auth: String
    ) : SongsDto

}
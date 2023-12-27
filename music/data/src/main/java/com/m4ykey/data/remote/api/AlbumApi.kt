package com.m4ykey.data.remote.api

import com.m4ykey.data.remote.model.album.AlbumResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.Locale

interface AlbumApi {

    @GET("browse/new-releases")
    suspend fun getNewReleases(
        @Header("Authorization") token : String,
        @Query("limit") limit : Int,
        @Query("offset") offset : Int,
        @Query("country") country : String = Locale.getDefault().country
    ) : AlbumResponseDto

}
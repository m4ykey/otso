package com.m4ykey.data.remote

import com.m4ykey.data.remote.album.model.AlbumResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.Locale

interface AlbumApi {

    @GET("browse/new-releases")
    suspend fun getNewReleases(
        @Query("limit") limit : Int,
        @Query("offset") offset : Int,
        @Query("country") country : String = Locale.getDefault().country,
        @Header("Authorization") token : String
    ) : AlbumResponseDto

}
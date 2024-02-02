package com.m4ykey.data.remote.api.spotify

import com.m4ykey.data.remote.model.album.AlbumDetailDto
import com.m4ykey.data.remote.model.album.AlbumResponseDto
import com.m4ykey.data.remote.model.album.tracks.TracksListDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
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

    @GET("albums/{id}")
    suspend fun getAlbumById(
        @Header("Authorization") token : String,
        @Path("id") albumId : String
    ) : AlbumDetailDto

    @GET("albums/{id}/tracks")
    suspend fun getAlbumTracks(
        @Header("Authorization") token : String,
        @Path("id") albumId : String,
        @Query("limit") limit : Int,
        @Query("offset") offset: Int,
        @Query("market") market : String = Locale.getDefault().country
    ) : TracksListDto

}
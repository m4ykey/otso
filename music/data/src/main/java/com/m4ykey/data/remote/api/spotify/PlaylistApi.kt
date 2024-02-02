package com.m4ykey.data.remote.api.spotify

import com.m4ykey.data.remote.model.playlist.PlaylistListDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.Locale

interface PlaylistApi {

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylists(
        @Query("locale") locale : String = Locale.getDefault().country,
        @Query("limit") limit : Int,
        @Query("offset") offset : Int,
        @Header("Authorization") token : String
    ) : PlaylistListDto

}
package com.m4ykey.data.remote.api

import com.m4ykey.data.remote.model.artist.ArtistDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ArtistApi {

    @GET("artists/{id}")
    suspend fun getArtistById(
        @Path("id") id : String,
        @Header("Authorization") token : String
    ) : ArtistDto

}
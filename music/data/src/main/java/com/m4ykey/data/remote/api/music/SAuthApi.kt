package com.m4ykey.data.remote.api.music

import com.m4ykey.data.remote.model.auth.SpotifyAuth
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface SAuthApi {

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAccessToken(
        @Header("Authorization") token : String,
        @Field("grant_type") grantType : String = "client_credentials"
    ) : SpotifyAuth

}
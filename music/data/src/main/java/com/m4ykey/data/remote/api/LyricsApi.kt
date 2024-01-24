package com.m4ykey.data.remote.api

import com.m4ykey.data.remote.model.auth.GeniusAuth
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface LyricsApi {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getGeniusToken(
        @Field("client_id") clientId : String,
        @Field("client_secret") clientSecret : String,
        @Field("grant_type") grantType : String = "client_credentials"
    ) : GeniusAuth

}
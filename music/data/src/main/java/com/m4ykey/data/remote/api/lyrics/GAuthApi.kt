package com.m4ykey.data.remote.api.lyrics

import com.m4ykey.data.remote.model.auth.GeniusAuth
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface GAuthApi {

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getGeniusToken(
        @Header("Authorization") token : String,
        @Field("grant_type") grantType : String = "client_credentials"
    ) : GeniusAuth

}
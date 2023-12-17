package com.m4ykey.data.remote

import com.m4ykey.data.remote.auth.Auth
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("api/token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Header("Authorization") authorization : String,
        @Field("grant_type") grantType : String
    ) : Auth

}
package com.m4ykey.data.remote.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Auth(
    @field:Json(name = "access_token") val accessToken : String,
    @field:Json(name = "token_type") val tokenType : String,
    @field:Json(name = "expires_in") val expiresIn : Int
)

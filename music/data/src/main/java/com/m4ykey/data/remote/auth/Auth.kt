package com.m4ykey.data.remote.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Auth(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)

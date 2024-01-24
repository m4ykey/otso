package com.m4ykey.data.remote.model.auth

data class SpotifyAuth(
    val access_token: String?,
    val token_type: String,
    val expires_in: Int
)

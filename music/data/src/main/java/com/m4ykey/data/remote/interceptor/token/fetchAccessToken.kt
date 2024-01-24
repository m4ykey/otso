package com.m4ykey.data.remote.interceptor.token

import android.util.Base64
import com.m4ykey.data.remote.api.AuthApi
import com.m4ykey.data.remote.api.LyricsApi

suspend fun <T: Any> fetchAccessToken(clientId : String, clientSecret : String, api : T) : String {
    val authHeader = "Basic " + Base64.encodeToString(
        "$clientId:$clientSecret".toByteArray(),
        Base64.NO_WRAP
    )

    try {
        when (api) {
            is AuthApi -> {
                val response = api.getAccessToken(
                    token = authHeader
                )

                if (response.access_token != null) {
                    return response.access_token
                } else {
                    throw RuntimeException("Failed to fetch access token")
                }
            }
            is LyricsApi -> {
                return ""
            }
            else -> throw IllegalArgumentException("Unsupported API type")
        }
    } catch (e : Exception) {
        throw RuntimeException("Error fetching access token", e)
    }
}
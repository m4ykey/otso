package com.m4ykey.data.remote.interceptor.token

import android.util.Base64
import com.m4ykey.data.remote.api.music.SAuthApi
import com.m4ykey.data.remote.api.lyrics.GAuthApi

suspend fun <T: Any> fetchAccessToken(clientId : String, clientSecret : String, api : T) : String {
    val authHeader = "Basic " + Base64.encodeToString(
        "$clientId:$clientSecret".toByteArray(),
        Base64.NO_WRAP
    )

    try {
        when (api) {
            is SAuthApi -> {
                val response = api.getAccessToken(
                    token = authHeader
                )

                if (response.access_token != null) {
                    return response.access_token
                } else {
                    throw RuntimeException("Failed to fetch access token")
                }
            }
            is GAuthApi -> {
                val response = api.getGeniusToken(
                    token = authHeader
                )

                if (response.access_token != null) {
                    return response.access_token
                } else {
                    throw RuntimeException("Failed to fetch access token")
                }
            }
            else -> throw IllegalArgumentException("Unsupported API type")
        }
    } catch (e : Exception) {
        throw RuntimeException("Error fetching access token", e)
    }
}
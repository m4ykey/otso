package com.m4ykey.data.remote.interceptor

import com.m4ykey.data.remote.api.LyricsApi
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GeniusTokenProvider @Inject constructor(
    private val api : LyricsApi
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}
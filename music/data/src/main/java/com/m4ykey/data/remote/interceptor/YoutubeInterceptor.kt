package com.m4ykey.data.remote.interceptor

import com.m4ykey.core.Keys
import okhttp3.Interceptor
import okhttp3.Response

class YoutubeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val urlWithApiKey = originalHttpUrl.newBuilder()
            .addQueryParameter("key", Keys.YOUTUBE_API_KEY)
            .build()

        val requestWithApi = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(requestWithApi)
    }
}

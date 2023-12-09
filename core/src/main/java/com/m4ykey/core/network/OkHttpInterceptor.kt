package com.m4ykey.core.network

import okhttp3.Interceptor
import okhttp3.Response

internal class OkHttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().url(request.url).build()
        return chain.proceed(newRequest)
    }
}
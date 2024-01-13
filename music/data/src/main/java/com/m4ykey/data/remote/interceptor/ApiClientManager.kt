package com.m4ykey.data.remote.interceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

enum class ApiType {
    SPOTIFY,
    YOUTUBE
}

@Singleton
class ApiClientManager @Inject constructor(
    @Named("spotifyInterceptor") private val spotifyInterceptor: SpotifyInterceptor,
    @Named("youtubeInterceptor") private val youtubeInterceptor: YoutubeInterceptor,
    private val loggingInterceptor: HttpLoggingInterceptor
) {

    fun provideOkHttpClient(apiType: ApiType) : OkHttpClient {
        val apiInterceptor = when (apiType) {
            ApiType.SPOTIFY -> spotifyInterceptor
            ApiType.YOUTUBE -> youtubeInterceptor
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
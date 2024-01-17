package com.m4ykey.data.di

import com.m4ykey.data.remote.interceptor.SpotifyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    private const val spotifyInterceptor = "spotifyInterceptor"

    @Provides
    @Singleton
    @Named(spotifyInterceptor)
    fun provideSpotifyInterceptor(
        loggingInterceptor: HttpLoggingInterceptor,
        spotifyInterceptor: SpotifyInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(spotifyInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

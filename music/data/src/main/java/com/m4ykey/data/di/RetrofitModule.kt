package com.m4ykey.data.di

import com.m4ykey.core.Constants.SPOTIFY_AUTH_URL
import com.m4ykey.core.Constants.SPOTIFY_BASE_URL
import com.m4ykey.core.Constants.YOUTUBE_BASE_URL
import com.m4ykey.core.network.createApi
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.api.AuthApi
import com.m4ykey.data.remote.api.VideoApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideAuth(
        @Named("auth") moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : AuthApi = createApi(SPOTIFY_AUTH_URL, moshi, AuthApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideAlbumApi(
        @Named("album") moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : AlbumApi = createApi(SPOTIFY_BASE_URL, moshi, AlbumApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideYoutubeApi(
        @Named("youtube") moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : VideoApi = createApi(YOUTUBE_BASE_URL, moshi, VideoApi::class.java, okHttpClient)

}
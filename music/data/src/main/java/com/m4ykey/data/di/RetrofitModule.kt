package com.m4ykey.data.di

import com.m4ykey.core.Constants.GENIUS_BASE_URL
import com.m4ykey.core.Constants.SPOTIFY_AUTH_URL
import com.m4ykey.core.Constants.SPOTIFY_BASE_URL
import com.m4ykey.core.Constants.YOUTUBE_BASE_URL
import com.m4ykey.core.network.createApi
import com.m4ykey.data.remote.api.lyrics.GAuthApi
import com.m4ykey.data.remote.api.lyrics.LyricsApi
import com.m4ykey.data.remote.api.music.AlbumApi
import com.m4ykey.data.remote.api.music.SAuthApi
import com.m4ykey.data.remote.api.music.TrackApi
import com.m4ykey.data.remote.api.video.VideoApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideSAuthApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : SAuthApi = createApi(SPOTIFY_AUTH_URL, moshi, SAuthApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideAlbumApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : AlbumApi = createApi(SPOTIFY_BASE_URL, moshi, AlbumApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideYoutubeApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : VideoApi = createApi(YOUTUBE_BASE_URL, moshi, VideoApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideGAuthApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : GAuthApi = createApi(GENIUS_BASE_URL, moshi, GAuthApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideLyricsApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : LyricsApi = createApi(GENIUS_BASE_URL, moshi, LyricsApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideTrackApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : TrackApi = createApi(SPOTIFY_BASE_URL, moshi, TrackApi::class.java, okHttpClient)

}
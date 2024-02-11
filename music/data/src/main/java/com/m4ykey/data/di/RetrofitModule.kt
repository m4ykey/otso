package com.m4ykey.data.di

import com.m4ykey.core.Constants.MUSIX_MATCH_BASE_URL
import com.m4ykey.core.Constants.SPOTIFY_AUTH_URL
import com.m4ykey.core.Constants.SPOTIFY_BASE_URL
import com.m4ykey.core.Constants.YOUTUBE_BASE_URL
import com.m4ykey.core.network.createApi
import com.m4ykey.data.remote.api.lyrics.LyricsApi
import com.m4ykey.data.remote.api.spotify.AlbumApi
import com.m4ykey.data.remote.api.spotify.ArtistApi
import com.m4ykey.data.remote.api.spotify.PlaylistApi
import com.m4ykey.data.remote.api.spotify.SAuthApi
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
    fun providePlaylistApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : PlaylistApi = createApi(SPOTIFY_BASE_URL, moshi, PlaylistApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideLyricsApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : LyricsApi = createApi(MUSIX_MATCH_BASE_URL, moshi, LyricsApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideArtistApi(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ) : ArtistApi = createApi(SPOTIFY_BASE_URL, moshi, ArtistApi::class.java, okHttpClient)

}
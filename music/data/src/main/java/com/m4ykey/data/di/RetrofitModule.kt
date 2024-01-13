package com.m4ykey.data.di

import com.m4ykey.core.Constants
import com.m4ykey.core.network.createApi
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.api.ArtistApi
import com.m4ykey.data.remote.api.AuthApi
import com.m4ykey.data.remote.api.VideoApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideAuth(
        @Named("auth") moshi: Moshi
    ) : AuthApi = createApi(Constants.SPOTIFY_AUTH_URL, moshi, AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAlbumApi(
        @Named("album") moshi: Moshi
    ) : AlbumApi = createApi(Constants.SPOTIFY_BASE_URL, moshi, AlbumApi::class.java)

    @Provides
    @Singleton
    fun provideArtistApi(
        @Named("artist") moshi: Moshi
    ) : ArtistApi = createApi(Constants.SPOTIFY_BASE_URL, moshi, ArtistApi::class.java)

    @Provides
    @Singleton
    fun provideYoutubeApi(
        @Named("youtube") moshi: Moshi
    ) : VideoApi = createApi(Constants.YOUTUBE_BASE_URL, moshi, VideoApi::class.java)

}
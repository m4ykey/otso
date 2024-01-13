package com.m4ykey.data.di

import com.m4ykey.core.network.createMoshi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthMoshi(): Moshi = createMoshi()

    @Provides
    @Singleton
    @Named("album")
    fun provideAlbumMoshi(): Moshi = createMoshi()

    @Provides
    @Singleton
    @Named("artist")
    fun provideArtistMoshi(): Moshi = createMoshi()

    @Provides
    @Singleton
    @Named("youtube")
    fun provideYoutubeMoshi(): Moshi = createMoshi()

}
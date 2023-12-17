package com.m4ykey.data.di

import com.m4ykey.core.Constants.SPOTIFY_AUTH_URL
import com.m4ykey.core.Constants.SPOTIFY_BASE_URL
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.data.remote.AlbumApi
import com.m4ykey.data.repository.AlbumRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlbumModule {

    @Provides
    @Singleton
    fun provideAlbumRepository(repository: AlbumRepositoryImpl) : AlbumRepository = repository

    @Provides
    @Singleton
    fun provideAuth(
        moshi: Moshi
    ) : Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(SPOTIFY_AUTH_URL)
        .build()

    @Provides
    @Singleton
    fun provideAlbumApi(
        moshi: Moshi,
        httpClient: OkHttpClient
    ) : AlbumApi = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(SPOTIFY_BASE_URL)
        .client(httpClient)
        .build()
        .create(AlbumApi::class.java)

}
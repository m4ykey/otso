package com.m4ykey.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.m4ykey.core.Constants.SPOTIFY_AUTH_URL
import com.m4ykey.core.Constants.SPOTIFY_BASE_URL
import com.m4ykey.core.network.createApi
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.api.AuthApi
import com.m4ykey.data.remote.interceptor.SpotifyInterceptor
import com.m4ykey.data.repository.AlbumRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
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
    ) : AuthApi = createApi(SPOTIFY_AUTH_URL, moshi, AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAlbumApi(
        moshi: Moshi
    ) : AlbumApi = createApi(SPOTIFY_BASE_URL, moshi, AlbumApi::class.java)

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "spotify_key")

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context : Context) : DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideSpotifyInterceptor(
        @Named("spotifyInterceptor") spotifyInterceptor: SpotifyInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(spotifyInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
}
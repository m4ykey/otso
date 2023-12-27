package com.m4ykey.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.m4ykey.core.Constants
import com.m4ykey.core.Constants.SPOTIFY_AUTH_URL
import com.m4ykey.core.Constants.SPOTIFY_BASE_URL
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.data.local.MusicDatabase
import com.m4ykey.data.local.converter.AlbumConverter
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
    ) : AuthApi = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(SPOTIFY_AUTH_URL)
        .build()
        .create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAlbumApi(
        moshi: Moshi,
    ) : AlbumApi = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(SPOTIFY_BASE_URL)
        .build()
        .create(AlbumApi::class.java)

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "spotify_key")

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context : Context) : DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideSpotifyInterceptor(
        spotifyInterceptor: SpotifyInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(spotifyInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideMusicDatabase(@ApplicationContext context: Context, moshi: Moshi) : MusicDatabase {
        val albumConverter = AlbumConverter(moshi = moshi)
        return Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            Constants.MUSIC_DATABASE
        )
            .addTypeConverter(albumConverter)
            .build()
    }

}
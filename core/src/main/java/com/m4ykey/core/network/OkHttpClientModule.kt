package com.m4ykey.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

}
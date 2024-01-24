package com.m4ykey.data.di

import com.m4ykey.data.remote.interceptor.token.CustomInterceptor
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

    @Provides
    @Singleton
    @Named("music")
    fun provideSpotifyInterceptor(
        loggingInterceptor: HttpLoggingInterceptor,
        customInterceptor: CustomInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            customInterceptor.intercept(chain)
        }
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

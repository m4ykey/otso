package com.m4ykey.data.di

import com.m4ykey.core.Constants.NEWS_BASE_URL
import com.m4ykey.core.network.createApi
import com.m4ykey.data.domain.repository.NewsRepository
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.repository.NewsRepositoryImpl
import com.squareup.moshi.Moshi
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
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(
        moshi : Moshi,
        okHttpClient : OkHttpClient
    ) : NewsApi = createApi(NEWS_BASE_URL, moshi, NewsApi::class.java, okHttpClient)

    @Provides
    @Singleton
    fun provideNewsRepository(repository: NewsRepositoryImpl) : NewsRepository = repository

    @Provides
    @Singleton
    @Named("news")
    fun provideNewsLoggingInterceptor(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

}
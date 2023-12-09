package com.m4ykey.data.di

import com.m4ykey.core.Constants
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.remote.repository.NewsRepository
import com.m4ykey.data.remote.repository.NewsRepositoryImpl
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
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        repository: NewsRepositoryImpl
    ) : NewsRepository = repository

    @Provides
    @Singleton
    fun provideNewsApi(
        httpClient: OkHttpClient,
        moshi : Moshi
    ) : NewsApi = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.NEWS_BASE_URL)
        .build()
        .create(NewsApi::class.java)

}
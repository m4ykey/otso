package com.m4ykey.data.di

import com.m4ykey.core.Constants
import com.m4ykey.data.domain.repository.NewsRepository
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.repository.NewsRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(
        moshi : Moshi
    ) : NewsApi = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.NEWS_BASE_URL)
        .build()
        .create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(repository: NewsRepositoryImpl) : NewsRepository = repository

}
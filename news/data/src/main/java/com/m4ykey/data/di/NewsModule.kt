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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(
        moshi : Moshi
    ) : NewsApi = createApi(NEWS_BASE_URL, moshi, NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(repository: NewsRepositoryImpl) : NewsRepository = repository

}
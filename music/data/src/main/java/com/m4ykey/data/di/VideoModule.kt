package com.m4ykey.data.di

import com.m4ykey.core.Constants.YOUTUBE_BASE_URL
import com.m4ykey.core.network.createApi
import com.m4ykey.data.domain.repository.VideoRepository
import com.m4ykey.data.remote.api.VideoApi
import com.m4ykey.data.repository.VideoRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoModule {

    @Provides
    @Singleton
    fun provideVideoRepository(repository : VideoRepositoryImpl) : VideoRepository = repository

    @Provides
    @Singleton
    fun provideVideoApi(
        moshi: Moshi
    ) : VideoApi = createApi(YOUTUBE_BASE_URL, moshi, VideoApi::class.java)

}
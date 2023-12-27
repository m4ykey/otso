package com.m4ykey.data.di

import com.m4ykey.core.Constants
import com.m4ykey.data.domain.repository.VideoRepository
import com.m4ykey.data.remote.api.VideoApi
import com.m4ykey.data.repository.VideoRepositoryImpl
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
object VideoModule {

    @Provides
    @Singleton
    fun provideVideoRepository(repository : VideoRepositoryImpl) : VideoRepository = repository

    @Provides
    @Singleton
    fun provideVideoApi(
        moshi: Moshi
    ) : VideoApi = Retrofit.Builder()
        .baseUrl(Constants.YOUTUBE_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(VideoApi::class.java)

}
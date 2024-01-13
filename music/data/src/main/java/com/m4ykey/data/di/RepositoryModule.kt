package com.m4ykey.data.di

import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.data.domain.repository.ArtistRepository
import com.m4ykey.data.domain.repository.VideoRepository
import com.m4ykey.data.repository.AlbumRepositoryImpl
import com.m4ykey.data.repository.ArtistRepositoryImpl
import com.m4ykey.data.repository.VideoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAlbumRepository(repository: AlbumRepositoryImpl) : AlbumRepository = repository

    @Provides
    @Singleton
    fun provideArtistRepository(repository : ArtistRepositoryImpl) : ArtistRepository = repository

    @Provides
    @Singleton
    fun provideVideoRepository(repository : VideoRepositoryImpl) : VideoRepository = repository

}
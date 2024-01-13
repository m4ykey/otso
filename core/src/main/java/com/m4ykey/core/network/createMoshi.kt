package com.m4ykey.core.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

fun createMoshi() : Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

@Module
@InstallIn(SingletonComponent::class)
object MoshiModule {

    @Provides
    @Singleton
    fun provideMoshi() : Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

}
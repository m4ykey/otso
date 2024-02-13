package com.m4ykey.core.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.m4ykey.core.network.NetworkStateMonitor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi() : Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkStateMonitor(connectivityManager: ConnectivityManager) : NetworkStateMonitor =
        NetworkStateMonitor(connectivityManager)

    @Provides
    @Singleton
    fun provideConnectivityManager(application: Application) : ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}
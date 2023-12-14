package com.m4ykey.data.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.m4ykey.core.Constants
import com.m4ykey.core.Constants.NEWS_DATABASE
import com.m4ykey.data.local.ArticleEntity
import com.m4ykey.data.local.NewsDatabase
import com.m4ykey.data.remote.NewsApi
import com.m4ykey.data.remote.paging.ArticleRemoteMediator
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideNewsApi(
        httpClient: OkHttpClient,
        moshi : Moshi
    ) : NewsApi = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.NEWS_BASE_URL)
        .build()
        .create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context : Context) : NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            NEWS_DATABASE
        ).fallbackToDestructiveMigration().build()

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideNewsPager(
        api : NewsApi,
        db : NewsDatabase
    ) : Pager<Int, ArticleEntity> = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = ArticleRemoteMediator(
            db = db,
            api = api
        ),
        pagingSourceFactory = {
            db.dao.pagingSource()
        }
    )

}
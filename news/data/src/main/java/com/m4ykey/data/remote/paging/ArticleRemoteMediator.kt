package com.m4ykey.data.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.m4ykey.data.local.ArticleEntity
import com.m4ykey.data.local.NewsDatabase
import com.m4ykey.data.mappers.toArticleEntity
import com.m4ykey.data.remote.NewsApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val db : NewsDatabase,
    private val api : NewsApi
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val news = api.getNews(
                page = loadKey,
                pageSize = state.config.pageSize
            )

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.dao.deleteAll()
                }
                val entities = news.articles.map { it.toArticleEntity() }
                db.dao.upsertAll(entities)
            }

            MediatorResult.Success(
                endOfPaginationReached = news.articles.isEmpty()
            )
        } catch (e : IOException) {
            MediatorResult.Error(e)
        } catch (e : HttpException) {
            MediatorResult.Error(e)
        }
    }
}
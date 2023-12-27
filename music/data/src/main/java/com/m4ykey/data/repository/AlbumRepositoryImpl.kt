package com.m4ykey.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.m4ykey.core.Constants.PAGE_SIZE
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.data.local.MusicDatabase
import com.m4ykey.data.mappers.toAlbums
import com.m4ykey.data.mappers.toNewRelease
import com.m4ykey.data.mappers.toNewReleaseEntity
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.interceptor.SpotifyInterceptor
import com.m4ykey.data.remote.paging.NewReleasePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val api : AlbumApi,
    private val interceptor : SpotifyInterceptor,
    private val db : MusicDatabase
) : AlbumRepository {
    override suspend fun getNewReleases(): Flow<Resource<List<Items>>> {
        return flow {
            emit(Resource.Loading())

            val cachedAlbums = withContext(Dispatchers.IO) {
                db.newReleaseDao.getAllNewReleases().map { it.toNewRelease() }
            }

            emit(Resource.Loading(data = cachedAlbums))

            try {
                val networkAlbums = api.getNewReleases(
                    limit = 10,
                    offset = 0,
                    token = "Bearer ${interceptor.getAccessToken()}"
                ).albums.toAlbums().items

                withContext(Dispatchers.IO) {
                    db.newReleaseDao.deleteAllNewReleases()
                    db.newReleaseDao.insertAllNewReleases(networkAlbums.map { it.toNewReleaseEntity() })
                }

                emit(Resource.Success(networkAlbums))
            } catch (e: Exception) {
                emit(Resource.Error(
                    message = e.localizedMessage ?: "An unexpected error occurred",
                    data = withContext(Dispatchers.IO) {
                        db.newReleaseDao.getAllNewReleases().map { it.toNewRelease() }
                    }
                ))
            }
        }
    }

    override fun getNewReleasePager(): Pager<Int, Items> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NewReleasePagingSource(api = api, interceptor = interceptor)
            }
        )
    }
}
package com.m4ykey.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.m4ykey.core.Constants.PAGE_SIZE
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.album.AlbumDetail
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.data.mappers.toAlbumDetail
import com.m4ykey.data.mappers.toAlbums
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.interceptor.SpotifyInterceptor
import com.m4ykey.data.remote.paging.NewReleasePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val api : AlbumApi,
    private val interceptor : SpotifyInterceptor
) : AlbumRepository {

    private val token = runBlocking { "Bearer ${interceptor.getAccessToken()}" }

    override suspend fun getNewReleases(): Flow<Resource<List<Items>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val albums = api.getNewReleases(
                    token = token,
                    limit = 10,
                    offset = 0
                ).albums.toAlbums().items
                emit(Resource.Success(albums))
            } catch (e : Exception) {
                emit(Resource.Error(
                    data = null,
                    message = e.localizedMessage ?: "An unexpected error occurred"
                ))
            }
        }
    }

    override suspend fun getAlbumById(albumId: String): Flow<Resource<AlbumDetail>> {
        return flow {
            emit(Resource.Loading())

            try {
                val album = api.getAlbumById(
                    token = token,
                    albumId = albumId
                ).toAlbumDetail()
                emit(Resource.Success(album))
            } catch (e : Exception) {
                emit(Resource.Error(
                    data = null,
                    message = e.localizedMessage ?: "An unexpected error occurred"
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
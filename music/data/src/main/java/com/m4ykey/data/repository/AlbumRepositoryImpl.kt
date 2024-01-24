package com.m4ykey.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.m4ykey.core.Constants.PAGE_SIZE
import com.m4ykey.core.network.Resource
import com.m4ykey.core.network.safeApiCall
import com.m4ykey.data.domain.model.album.AlbumDetail
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.data.mappers.toAlbumDetail
import com.m4ykey.data.mappers.toAlbums
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.interceptor.SpotifyTokenProvider
import com.m4ykey.data.remote.paging.NewReleasePagingSource
import com.m4ykey.data.remote.paging.TrackListPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val api : AlbumApi,
    private val interceptor : SpotifyTokenProvider
) : AlbumRepository {

    private val token = runBlocking { "Bearer ${interceptor.getAccessToken()}" }

        override suspend fun getNewReleases(): Flow<Resource<List<Items>>> = flow {
            emit(Resource.Loading())
            emit(safeApiCall {
                api.getNewReleases(
                    token = token,
                    limit = 10,
                    offset = 0
                ).albums.toAlbums().items
            })
        }

    override suspend fun getAlbumById(albumId: String): Flow<Resource<AlbumDetail>> = flow {
        emit(Resource.Loading())
        emit(safeApiCall {
            api.getAlbumById(
                token = token,
                albumId = albumId
            ).toAlbumDetail()
        })
    }

    override fun getTrackListPager(albumId: String): Flow<PagingData<TrackItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TrackListPagingSource(api = api, interceptor = interceptor, albumId = albumId)
            }
        ).flow
    }

    override fun getNewReleasePager(): Flow<PagingData<Items>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewReleasePagingSource(
                api = api,
                interceptor = interceptor
            ) }
        ).flow
    }
}
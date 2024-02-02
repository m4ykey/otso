package com.m4ykey.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.m4ykey.core.Constants.PAGE_SIZE
import com.m4ykey.core.network.Resource
import com.m4ykey.core.network.safeApiCall
import com.m4ykey.data.domain.model.playlist.PlaylistItems
import com.m4ykey.data.domain.repository.PlaylistRepository
import com.m4ykey.data.mappers.toPlaylists
import com.m4ykey.data.remote.api.spotify.PlaylistApi
import com.m4ykey.data.remote.interceptor.SpotifyTokenProvider
import com.m4ykey.data.remote.paging.PlaylistPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val interceptor : SpotifyTokenProvider,
    private val api : PlaylistApi
) : PlaylistRepository {

    private val token = runBlocking { "Bearer ${interceptor.getAccessToken()}" }

    override suspend fun getFeaturedPlaylists(): Flow<Resource<List<PlaylistItems>>> = flow {
        emit(Resource.Loading())
        emit(safeApiCall {
            api.getFeaturedPlaylists(
                token = token,
                limit = 10,
                offset = 0
            ).playlists.toPlaylists().items
        })
    }

    override fun getFeaturedPlaylistPager(): Flow<PagingData<PlaylistItems>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PlaylistPagingSource(api = api, interceptor = interceptor)
            }
        ).flow
    }
}
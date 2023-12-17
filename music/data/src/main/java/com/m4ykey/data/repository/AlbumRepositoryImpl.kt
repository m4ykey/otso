package com.m4ykey.data.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.Items
import com.m4ykey.data.domain.repository.AlbumRepository
import com.m4ykey.data.mappers.toItems
import com.m4ykey.data.remote.AlbumApi
import com.m4ykey.data.remote.interceptor.SpotifyInterceptor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val api : AlbumApi,
    private val interceptor : SpotifyInterceptor
) : AlbumRepository {
    override suspend fun getNewReleases(): Flow<Resource<List<Items>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val albums = api.getNewReleases(
                    limit = 10,
                    offset = 0,
                    token = "Bearer ${interceptor.getAccessToken()}"
                ).albums.items.map { it.toItems() }
                emit(Resource.Success(albums))
            } catch (e: Exception) {
                emit(Resource.Error(
                    message = e.localizedMessage ?: "An unexpected error occurred",
                    data = null
                ))
            }
        }
    }
}
package com.m4ykey.data.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.core.network.safeApiCall
import com.m4ykey.data.domain.model.track.Track
import com.m4ykey.data.domain.repository.TrackRepository
import com.m4ykey.data.mappers.toTrack
import com.m4ykey.data.remote.api.music.TrackApi
import com.m4ykey.data.remote.interceptor.SpotifyTokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val api : TrackApi,
    private val interceptor : SpotifyTokenProvider
) : TrackRepository {
    override suspend fun getTrackImage(query: String): Flow<Resource<List<Track>>> = flow {
        emit(Resource.Loading())
        emit(safeApiCall {
            api.getTrackImage(
                query = query,
                token = "Bearer ${interceptor.getAccessToken()}"
            ).tracks.items.map { it.toTrack() }
        })
    }
}
package com.m4ykey.data.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.core.network.safeApiCall
import com.m4ykey.data.domain.model.lyrics.Track
import com.m4ykey.data.domain.model.lyrics.TrackMessage
import com.m4ykey.data.domain.repository.LyricsRepository
import com.m4ykey.data.mappers.toTrack
import com.m4ykey.data.mappers.toTrackMessage
import com.m4ykey.data.remote.api.lyrics.LyricsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LyricsRepositoryImpl @Inject constructor(
    private val api : LyricsApi
) : LyricsRepository {
    override suspend fun searchTrack(track: String, artist: String): Flow<Resource<List<Track>>> = flow {
        emit(Resource.Loading())
        emit(safeApiCall {
            api.searchTrack(
                track = track,
                artist = artist
            ).message.body.track_list.map { it.track.toTrack() }
        })
    }

    override suspend fun getTrackLyrics(id: Int): Flow<Resource<TrackMessage>> = flow {
        emit(Resource.Loading())
        val apiResult = safeApiCall { api.getTrackLyrics(id = id) }

        if (apiResult is Resource.Success) {
            emit(Resource.Success(apiResult.data!!.message.toTrackMessage()))
        } else {
            emit(Resource.Error(message = apiResult.message ?: "Unknown error"))
        }
    }
}
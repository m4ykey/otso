package com.m4ykey.data.domain.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.track.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

    suspend fun getTrackImage(query : String) : Flow<Resource<List<Track>>>

}
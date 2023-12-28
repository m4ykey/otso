package com.m4ykey.data.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.core.network.safeApiCall
import com.m4ykey.data.domain.model.video.VideoItem
import com.m4ykey.data.domain.repository.VideoRepository
import com.m4ykey.data.mappers.toVideoItem
import com.m4ykey.data.remote.api.VideoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val api : VideoApi
) : VideoRepository {
    override suspend fun getMostPopularVideos(): Flow<Resource<List<VideoItem>>> = flow {
        emit(Resource.Loading())
        emit(safeApiCall {
            api.getMostPopularVideos().items.map { it.toVideoItem() }
        })
    }
}
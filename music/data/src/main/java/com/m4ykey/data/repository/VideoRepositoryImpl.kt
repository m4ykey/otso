package com.m4ykey.data.repository

import com.m4ykey.core.network.Resource
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
    override suspend fun getMostPopularVideos(): Flow<Resource<List<VideoItem>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val videos = api.getMostPopularVideos().items.map { it.toVideoItem() }
                emit(Resource.Success(videos))
            } catch (e : Exception) {
                emit(Resource.Error(
                    data = null,
                    message = e.localizedMessage ?: "An unexpected error occurred"
                ))
            }
        }
    }
}
package com.m4ykey.data.domain.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.video.VideoItem
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getMostPopularVideos() : Flow<Resource<List<VideoItem>>>

}
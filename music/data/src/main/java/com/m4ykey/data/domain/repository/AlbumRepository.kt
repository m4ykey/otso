package com.m4ykey.data.domain.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.Items
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun getNewReleases() : Flow<Resource<List<Items>>>

}
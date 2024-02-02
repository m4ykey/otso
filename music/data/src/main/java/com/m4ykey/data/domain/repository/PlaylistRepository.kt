package com.m4ykey.data.domain.repository

import androidx.paging.PagingData
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.playlist.PlaylistItems
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun getFeaturedPlaylists() : Flow<Resource<List<PlaylistItems>>>
    fun getFeaturedPlaylistPager() : Flow<PagingData<PlaylistItems>>
}
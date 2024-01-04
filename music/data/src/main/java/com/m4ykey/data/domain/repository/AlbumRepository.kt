package com.m4ykey.data.domain.repository

import androidx.paging.PagingData
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.album.AlbumDetail
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun getNewReleases() : Flow<Resource<List<Items>>>
    fun getNewReleasePager() : Flow<PagingData<Items>>
    suspend fun getAlbumById(albumId : String) : Flow<Resource<AlbumDetail>>
    fun getTrackListPager(albumId: String) : Flow<PagingData<TrackItem>>

}
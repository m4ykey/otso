package com.m4ykey.data.domain.repository

import androidx.paging.Pager
import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.album.AlbumDetail
import com.m4ykey.data.domain.model.album.Items
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun getNewReleases() : Flow<Resource<List<Items>>>
    fun getNewReleasePager() : Pager<Int, Items>
    suspend fun getAlbumById(albumId : String) : Flow<Resource<AlbumDetail>>

}
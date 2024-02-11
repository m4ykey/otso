package com.m4ykey.data.domain.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.artist.Artist
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {

    suspend fun getArtistById(id : String) : Flow<Resource<Artist>>

}
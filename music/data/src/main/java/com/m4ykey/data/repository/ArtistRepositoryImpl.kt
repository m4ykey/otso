package com.m4ykey.data.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.core.network.safeApiCall
import com.m4ykey.data.domain.model.artist.Artist
import com.m4ykey.data.domain.repository.ArtistRepository
import com.m4ykey.data.mappers.toArtist
import com.m4ykey.data.remote.api.spotify.ArtistApi
import com.m4ykey.data.remote.interceptor.SpotifyTokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val api : ArtistApi,
    private val tokenProvider : SpotifyTokenProvider
) : ArtistRepository {

    private val token = runBlocking { "Bearer ${tokenProvider.getAccessToken()}" }

    override suspend fun getArtistById(id: String): Flow<Resource<Artist>> = flow {
        emit(Resource.Loading())
        emit(safeApiCall {
            api.getArtistById(id = id, token = token).toArtist()
        })
    }
}
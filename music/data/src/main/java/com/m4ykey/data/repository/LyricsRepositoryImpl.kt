package com.m4ykey.data.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.core.network.safeApiCall
import com.m4ykey.data.domain.model.lyrics.SongResult
import com.m4ykey.data.domain.model.lyrics.Song
import com.m4ykey.data.domain.repository.LyricsRepository
import com.m4ykey.data.mappers.toSongResult
import com.m4ykey.data.mappers.toSong
import com.m4ykey.data.remote.api.lyrics.LyricsApi
import com.m4ykey.data.remote.interceptor.GeniusTokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LyricsRepositoryImpl @Inject constructor(
    private val lyricsApi: LyricsApi,
    private val tokenProvider : GeniusTokenProvider
) : LyricsRepository {

    private val token = runBlocking { "Bearer ${tokenProvider.getAccessToken()}" }

    override suspend fun searchLyrics(query: String): Flow<Resource<List<SongResult>>> = flow {
        emit(Resource.Loading())
        emit(safeApiCall {
            val response = lyricsApi.searchLyrics(
                query = query,
                auth = token
            )

            val hits = response.response.hits

            if (hits.isNotEmpty()) {
                val path = hits[0].result.toSongResult().id.toString()

                try {
                    val lyricsResponse = lyricsApi.searchLyrics(auth = token, query = path)
                    lyricsResponse.response.hits[0].result.full_title
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                listOf(hits[0].result.toSongResult())
            } else {
                emptyList()
            }
        })
    }

    override suspend fun getLyrics(id: String): Flow<Resource<Song>> = flow {
        emit(Resource.Loading())

        emit(safeApiCall {
            val response = lyricsApi.getLyrics(auth = token, id = id)
            val song = response.response.song.toSong()

            val embedContent = response.response.song.embed_content
            song.embedContent = embedContent
            song
        })
    }
}
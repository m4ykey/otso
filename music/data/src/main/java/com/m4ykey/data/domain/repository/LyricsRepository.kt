package com.m4ykey.data.domain.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.lyrics.SongResult
import com.m4ykey.data.domain.model.lyrics.Song
import kotlinx.coroutines.flow.Flow

interface LyricsRepository {

    suspend fun searchLyrics(query : String) : Flow<Resource<List<SongResult>>>
    suspend fun getLyrics(id : String) : Flow<Resource<Song>>

}
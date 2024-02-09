package com.m4ykey.data.domain.repository

import com.m4ykey.core.network.Resource
import com.m4ykey.data.domain.model.lyrics.Lyrics
import com.m4ykey.data.domain.model.lyrics.Track
import kotlinx.coroutines.flow.Flow

interface LyricsRepository {

    suspend fun searchTrack(track : String, artist : String) : Flow<Resource<List<Track>>>
    suspend fun getTrackLyrics(id : Int) : Flow<Resource<Lyrics>>

}
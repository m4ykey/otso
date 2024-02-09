package com.m4ykey.data.remote.api.lyrics

import com.m4ykey.data.BuildConfig.MUSIXMATCH_API_KEY
import com.m4ykey.data.remote.model.lyrics.SearchLyricsDto
import com.m4ykey.data.remote.model.lyrics.TrackLyricsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LyricsApi {

    @GET("track.search")
    suspend fun searchTrack(
        @Query("q_track") track : String,
        @Query("q_artist") artist : String,
        @Query("apikey") apikey : String = MUSIXMATCH_API_KEY
    ) : SearchLyricsDto

    @GET("track.lyrics.get")
    suspend fun getTrackLyrics(
        @Query("apikey") apikey : String = MUSIXMATCH_API_KEY,
        @Query("track_id") id : Int
    ) : TrackLyricsDto

}
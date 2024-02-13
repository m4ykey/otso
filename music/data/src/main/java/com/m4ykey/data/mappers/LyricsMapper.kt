package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.lyrics.Header
import com.m4ykey.data.domain.model.lyrics.Lyrics
import com.m4ykey.data.domain.model.lyrics.Track
import com.m4ykey.data.domain.model.lyrics.TrackBody
import com.m4ykey.data.domain.model.lyrics.TrackMessage
import com.m4ykey.data.remote.model.lyrics.HeaderDto
import com.m4ykey.data.remote.model.lyrics.LyricsDto
import com.m4ykey.data.remote.model.lyrics.TrackBodyDto
import com.m4ykey.data.remote.model.lyrics.TrackDto
import com.m4ykey.data.remote.model.lyrics.TrackMessageDto

fun LyricsDto.toLyrics() : Lyrics {
    return Lyrics(
        lyricsId = lyrics_id,
        lyricsBody = lyrics_body,
        lyricsCopyright = lyrics_copyright,
    )
}

fun TrackDto.toTrack() : Track {
    return Track(
        albumName = album_name,
        artistName = artist_name,
        trackId = track_id,
        trackName = track_name
    )
}

fun TrackBodyDto.toTrackBody() : TrackBody = TrackBody(lyrics = lyrics.toLyrics())

fun TrackMessageDto.toTrackMessage() : TrackMessage = TrackMessage(body = body.toTrackBody(), header = header.toHeader())

fun HeaderDto.toHeader() : Header = Header(statusCode = status_code)
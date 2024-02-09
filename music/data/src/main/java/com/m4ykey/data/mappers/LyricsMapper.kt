package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.lyrics.Body
import com.m4ykey.data.domain.model.lyrics.Lyrics
import com.m4ykey.data.domain.model.lyrics.Message
import com.m4ykey.data.domain.model.lyrics.SearchLyrics
import com.m4ykey.data.domain.model.lyrics.Track
import com.m4ykey.data.domain.model.lyrics.TrackBody
import com.m4ykey.data.domain.model.lyrics.TrackList
import com.m4ykey.data.domain.model.lyrics.TrackLyrics
import com.m4ykey.data.domain.model.lyrics.TrackMessage
import com.m4ykey.data.remote.model.lyrics.BodyDto
import com.m4ykey.data.remote.model.lyrics.LyricsDto
import com.m4ykey.data.remote.model.lyrics.MessageDto
import com.m4ykey.data.remote.model.lyrics.SearchLyricsDto
import com.m4ykey.data.remote.model.lyrics.TrackBodyDto
import com.m4ykey.data.remote.model.lyrics.TrackDto
import com.m4ykey.data.remote.model.lyrics.TrackListDto
import com.m4ykey.data.remote.model.lyrics.TrackLyricsDto
import com.m4ykey.data.remote.model.lyrics.TrackMessageDto

fun LyricsDto.toLyrics() : Lyrics {
    return Lyrics(
        lyricsId = lyrics_id,
        lyricsBody = lyrics_body,
        lyricsCopyright = lyrics_copyright,
    )
}

fun MessageDto.toMessage() : Message = Message(body = body.toBody())

fun SearchLyricsDto.toSearchLyrics() : SearchLyrics = SearchLyrics(message = message.toMessage())

fun BodyDto.toBody() : Body = Body(trackList = track_list.map { it.toTrackList() })

fun TrackDto.toTrack() : Track {
    return Track(
        albumName = album_name,
        artistName = artist_name,
        trackId = track_id,
        trackName = track_name
    )
}

fun TrackBodyDto.toTrackBody() : TrackBody = TrackBody(lyrics = lyrics.toLyrics())

fun TrackListDto.toTrackList() : TrackList = TrackList(track = track.toTrack())

fun TrackLyricsDto.toTrackLyrics() : TrackLyrics = TrackLyrics(message = message.toTrackMessage())

fun TrackMessageDto.toTrackMessage() : TrackMessage = TrackMessage(body = body.toTrackBody())
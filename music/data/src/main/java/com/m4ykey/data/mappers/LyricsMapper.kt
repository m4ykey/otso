package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.lyrics.Hit
import com.m4ykey.data.domain.model.lyrics.LyricsList
import com.m4ykey.data.domain.model.lyrics.Media
import com.m4ykey.data.domain.model.lyrics.ProducerArtist
import com.m4ykey.data.domain.model.lyrics.Response
import com.m4ykey.data.domain.model.lyrics.SongResult
import com.m4ykey.data.domain.model.lyrics.Song
import com.m4ykey.data.domain.model.lyrics.SongResponse
import com.m4ykey.data.domain.model.lyrics.Songs
import com.m4ykey.data.domain.model.lyrics.Stats
import com.m4ykey.data.remote.model.lyrics.HitDto
import com.m4ykey.data.remote.model.lyrics.LyricsListDto
import com.m4ykey.data.remote.model.lyrics.MediaDto
import com.m4ykey.data.remote.model.lyrics.ProducerArtistDto
import com.m4ykey.data.remote.model.lyrics.ResponseDto
import com.m4ykey.data.remote.model.lyrics.SongResultDto
import com.m4ykey.data.remote.model.lyrics.SongDto
import com.m4ykey.data.remote.model.lyrics.SongResponseDto
import com.m4ykey.data.remote.model.lyrics.SongsDto
import com.m4ykey.data.remote.model.lyrics.StatsDto

fun StatsDto.toStats() : Stats {
    return Stats(pageViews = pageviews)
}

fun SongResultDto.toSongResult() : SongResult {
    return SongResult(
        id = id,
        fullTitle = full_title
    )
}

fun HitDto.toHit() : Hit {
    return Hit(result = result.toSongResult())
}

fun LyricsListDto.toLyricsList() : LyricsList {
    return LyricsList(response = response.toResponse())
}

fun MediaDto.toMedia() : Media {
    return Media(url = url)
}

fun ProducerArtistDto.toProducerArtist() : ProducerArtist {
    return ProducerArtist(
        imageUrl = image_url,
        name = name,
        url = url
    )
}

fun ResponseDto.toResponse() : Response {
    return Response(
        hits = hits.map { it.toHit() }
    )
}

fun SongDto.toSong() : Song {
    return Song(
        appleMusicId = apple_music_id,
        appleMusicPlayerUrl = apple_music_player_url,
        artistNames = artist_names,
        embedContent = embed_content,
        featuredArtists = featured_artists.map { it },
        headerImageUrl = header_image_url,
        id = id,
        media = media.map { it.toMedia() },
        title = title,
        url = url,
        stats = stats.toStats(),
        releaseDateForDisplay = release_date_for_display,
        releaseDateWithAbbreviatedMonthForDisplay = release_date_with_abbreviated_month_for_display,
        songArtImageUrl = song_art_image_url,
        headerImageThumbnailUrl = header_image_thumbnail_url,
        producerArtists = producer_artists.map { it.toProducerArtist() }
    )
}

fun SongResponseDto.toSongResponse() : SongResponse {
    return SongResponse(song = song.toSong())
}

fun SongsDto.toSongs() : Songs {
    return Songs(
        response = response.toSongResponse()
    )
}
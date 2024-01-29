package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.lyrics.Media
import com.m4ykey.data.domain.model.lyrics.ProducerArtist
import com.m4ykey.data.domain.model.lyrics.Song
import com.m4ykey.data.domain.model.lyrics.SongResult
import com.m4ykey.data.domain.model.lyrics.Stats
import com.m4ykey.data.remote.model.lyrics.MediaDto
import com.m4ykey.data.remote.model.lyrics.ProducerArtistDto
import com.m4ykey.data.remote.model.lyrics.SongDto
import com.m4ykey.data.remote.model.lyrics.SongResultDto
import com.m4ykey.data.remote.model.lyrics.StatsDto

fun StatsDto.toStats() : Stats = Stats(pageViews = pageviews!!)

fun SongResultDto.toSongResult() : SongResult = SongResult(id = id)

fun MediaDto.toMedia() : Media = Media(url = url!!)

fun ProducerArtistDto.toProducerArtist() : ProducerArtist {
    return ProducerArtist(
        imageUrl = image_url!!,
        name = name!!,
        url = url!!
    )
}

fun SongDto.toSong() : Song {
    return Song(
        appleMusicId = apple_music_id!!,
        appleMusicPlayerUrl = apple_music_player_url!!,
        artistNames = artist_names!!,
        embedContent = embed_content!!,
        headerImageUrl = header_image_url!!,
        id = id,
        media = media.map { it.toMedia() },
        title = title!!,
        url = url!!,
        stats = stats.toStats(),
        releaseDateForDisplay = release_date_for_display!!,
        releaseDateWithAbbreviatedMonthForDisplay = release_date_with_abbreviated_month_for_display!!,
        songArtImageUrl = song_art_image_url!!,
        headerImageThumbnailUrl = header_image_thumbnail_url!!,
        producerArtists = producer_artists.map { it.toProducerArtist() }
    )
}
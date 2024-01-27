package com.m4ykey.data.domain.model.lyrics

data class Song(
    val appleMusicId: String,
    val appleMusicPlayerUrl: String,
    val artistNames: String,
    var embedContent: String,
    val featuredArtists: List<String>,
    val headerImageThumbnailUrl: String,
    val headerImageUrl: String,
    val id: Int,
    val media: List<Media>,
    val producerArtists: List<ProducerArtist>,
    val releaseDateForDisplay: String,
    val releaseDateWithAbbreviatedMonthForDisplay: String,
    val songArtImageUrl: String,
    val stats: Stats,
    val title: String,
    val url: String
)
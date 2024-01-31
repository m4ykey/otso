package com.m4ykey.data.domain.model.lyrics

data class Song(
    val appleMusicId: String? = null,
    val appleMusicPlayerUrl: String? = null,
    val artistNames: String? = null,
    var embedContent: String? = null,
    val headerImageThumbnailUrl: String? = null,
    val headerImageUrl: String? = null,
    val id: Int,
    val media: List<Media>,
    val producerArtists: List<ProducerArtist>,
    val releaseDateForDisplay: String? = null,
    val releaseDateWithAbbreviatedMonthForDisplay: String? = null,
    val songArtImageUrl: String? = null,
    val stats: Stats,
    val title: String? = null,
    val url: String? = null,
    val path : String? = null
)
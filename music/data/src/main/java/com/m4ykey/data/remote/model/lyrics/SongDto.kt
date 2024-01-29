package com.m4ykey.data.remote.model.lyrics

data class SongDto(
    val apple_music_id: String? = "",
    val apple_music_player_url: String? = "",
    val artist_names: String? = "",
    val embed_content: String? = "",
    //val featured_artists: List<String>? = emptyList(),
    val header_image_thumbnail_url: String? = "",
    val header_image_url: String? = "",
    val id: Int,
    val media: List<MediaDto>,
    val producer_artists: List<ProducerArtistDto>,
    val release_date_for_display: String? = "",
    val release_date_with_abbreviated_month_for_display: String? = "",
    val song_art_image_url: String? = "",
    val stats: StatsDto,
    val title: String? = "",
    val url: String? = ""
)
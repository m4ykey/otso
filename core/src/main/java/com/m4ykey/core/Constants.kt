package com.m4ykey.core

object Constants {

    const val PAGE_SIZE = 20

    const val NEWS_BASE_URL = "https://newsapi.org/"
    const val DOMAINS = "${MusicSources.ROLLING_STONE}, ${MusicSources.VARIETY}, ${MusicSources.BILLBOARD}, " +
            "${MusicSources.PITCHFORK}, ${MusicSources.NBC_NEWS}"

    const val SPOTIFY_AUTH_URL = "https://accounts.spotify.com/"
    const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1/"
    const val NEW_RELEASE_TABLE = "new_release_table"
    const val MUSIC_DATABASE = "music_db"

}

object MusicSources {
    const val ROLLING_STONE = "rollingstone.com"
    const val BILLBOARD = "billboard.com"
    const val PITCHFORK = "pitchfork.com"
    const val VARIETY = "variety.com/v/music"
    const val NBC_NEWS = "nbcnews.com/news"
}
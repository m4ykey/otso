package com.m4ykey.core

object Constants {

    const val NEWS_BASE_URL = "https://newsapi.org/"
    const val NEWS_DATABASE = "news_db"
    const val ARTICLE_TABLE = "article_table"
    const val DOMAINS = "${MusicSources.BBC_MUSIC}," + "${MusicSources.MTV}," +
            "${MusicSources.ROLLING_STONE}," + "${MusicSources.NME}," +
            "${MusicSources.SPIN}," + "${MusicSources.BILLBOARD}," +
            "${MusicSources.PITCHFORK}," + "${MusicSources.MUSIC_NEWS}," +
            "${MusicSources.THE_GUARDIAN}," + "${MusicSources.NEW_YORK_TIMES}," +
            "${MusicSources.VARIETY}," + MusicSources.NBC_NEWS

    const val SPOTIFY_AUTH_URL = "https://accounts.spotify.com/"
    const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1/"

}

object MusicSources {
    const val ROLLING_STONE = "rollingstone.com"
    const val BILLBOARD = "billboard.com"
    const val MTV = "mtv.com"
    const val NME = "nme.com"
    const val PITCHFORK = "pitchfork.com"
    const val SPIN = "spin.com"
    const val BBC_MUSIC = "bbc.co.uk/music"
    const val MUSIC_NEWS = "music-news.com"
    const val THE_GUARDIAN = "theguardian.com/music"
    const val NEW_YORK_TIMES = "nytimes.com/international/section/arts/music"
    const val VARIETY = "variety.com/v/music"
    const val NBC_NEWS = "nbcnews.com/pop-culture/music"
}
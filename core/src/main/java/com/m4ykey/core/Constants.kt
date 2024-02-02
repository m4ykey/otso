package com.m4ykey.core

object Constants {

    const val PAGE_SIZE = 20

    const val NEWS_BASE_URL = "https://newsapi.org/"
    const val DOMAINS =
        "${MusicSources.ROLLING_STONE}, ${MusicSources.BILLBOARD}, " + MusicSources.PITCHFORK
    const val ROLLING_STONE_LOGO = "https://play-lh.googleusercontent.com/ToohFdJJ016UxyObeFjWkB6wtp3_M-DiSaPvCJqV19kU_k0mGZ7SJPKrodbNJw4KGT4=w240-h480-rw"
    const val PITCHFORK_LOGO = "https://yt3.googleusercontent.com/ytc/AIf8zZS9edaTMwek-1vVJgY6yP5mMXfzQo0nyvnmD4arHQ=s900-c-k-c0x00ffffff-no-rj"
    const val BILLBOARD_LOGO = "https://upload.wikimedia.org/wikipedia/commons/0/0d/Billboard-logo-web.png"

    const val SPOTIFY_AUTH_URL = "https://accounts.spotify.com/"
    const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1/"

    const val YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/"

    const val CHANNEL_ID = "Music_Channel_ID"
    const val NOTIFICATION_ID = 1

    const val SPOTIFY_LOGO = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Spotify_logo_without_text.svg/2048px-Spotify_logo_without_text.svg.png"
    const val YT_MUSIC_LOGO = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Youtube_Music_icon.svg/512px-Youtube_Music_icon.svg.png"
    const val DEEZER_LOGO = "https://w7.pngwing.com/pngs/958/783/png-transparent-deezer-app-logo-tech-companies-thumbnail.png"
    const val SOUNDCLOUD_LOGO = "https://w7.pngwing.com/pngs/183/887/png-transparent-soundcloud-logo-soundcloud-computer-icons-logo-soundcloud-logo-orange-desktop-wallpaper-music-download-thumbnail.png"
    const val TIDAL_LOGO = "https://w7.pngwing.com/pngs/973/746/png-transparent-tidal-round-logo-tech-companies-thumbnail.png"
    const val SHAZAM_LOGO = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/Shazam_icon.svg/800px-Shazam_icon.svg.png"

}

object MusicSources {
    const val ROLLING_STONE = "rollingstone.com"
    const val BILLBOARD = "billboard.com"
    const val PITCHFORK = "pitchfork.com"
}
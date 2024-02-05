package com.m4ykey.core

object Constants {

    const val PAGE_SIZE = 20

    const val NEWS_BASE_URL = "https://newsapi.org/"
    const val DOMAINS =
        "${MusicSources.ROLLING_STONE}, ${MusicSources.BILLBOARD}, ${MusicSources.PITCHFORK}, ${MusicSources.INTERIA}, ${MusicSources.NME}, " +
                "${MusicSources.CONSEQUENCE}, ${MusicSources.STEREOGUM}, ${MusicSources.THE_FADER}"
    const val ROLLING_STONE_LOGO = "https://play-lh.googleusercontent.com/ToohFdJJ016UxyObeFjWkB6wtp3_M-DiSaPvCJqV19kU_k0mGZ7SJPKrodbNJw4KGT4=w240-h480-rw"
    const val PITCHFORK_LOGO = "https://yt3.googleusercontent.com/ytc/AIf8zZS9edaTMwek-1vVJgY6yP5mMXfzQo0nyvnmD4arHQ=s900-c-k-c0x00ffffff-no-rj"
    const val BILLBOARD_LOGO = "https://upload.wikimedia.org/wikipedia/commons/0/0d/Billboard-logo-web.png"
    const val INTERIA_LOGO = "https://firma.interia.pl/static/caf57bd01b7d42c6bc1d783e83a75d15/5bec7/Component-35-E28093-1.png"
    const val STEREOGUM_LOGO = "https://pbs.twimg.com/profile_images/1325696974578913280/NRN47Aw-_400x400.jpg"
    const val NME_LOGO = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/NME_logo_free.svg/2560px-NME_logo_free.svg.png"
    const val CONSEQUENCE_LOGO = "https://upload.wikimedia.org/wikipedia/en/9/95/Consequence-main-logo.jpg"
    const val THE_FADER_LOGO = "https://www.thefader.com/assets/Fader-New-Black-FB-d2aaa59a0df7666f2ffae62bf70a70896fa5d60656f2c33d97f9ce70d98a2209.png"

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
    const val NME = "nme.com"
    const val CONSEQUENCE = "consequence.net"
    const val STEREOGUM = "stereogum.com"
    const val THE_FADER = "thefader.com"
    const val INTERIA = "interia.pl"
}
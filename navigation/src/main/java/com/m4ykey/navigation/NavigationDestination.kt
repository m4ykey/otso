package com.m4ykey.navigation

interface NavigationDestination {
    val route : String
}

object Music {
    object MusicDestination : NavigationDestination {
        override val route = "music"
    }
    object AlbumDetailDestination : NavigationDestination {
        override val route = "album_detail"
    }
    object NewReleaseDestination : NavigationDestination {
        override val route = "new_release"
    }
    object PlaylistDestination : NavigationDestination {
        override val route = "playlist"
    }
    object SearchDestination : NavigationDestination {
        override val route = "search"
    }
    object LyricsDestination : NavigationDestination {
        override val route = "lyrics"
    }
}

object News {
    object NewsDestination : NavigationDestination {
        override val route = "news"
    }
}

object Tools {
    object ToolsDestination : NavigationDestination {
        override val route = "tools"
    }
}
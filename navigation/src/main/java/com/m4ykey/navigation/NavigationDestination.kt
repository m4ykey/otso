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
    object SongDestination : NavigationDestination {
        override val route = "song"
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
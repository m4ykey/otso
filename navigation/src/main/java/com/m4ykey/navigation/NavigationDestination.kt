package com.m4ykey.navigation

interface NavigationDestination {
    val route : String
}

object NewsDestination : NavigationDestination {
    override val route = "news"
}

object MusicDestination : NavigationDestination {
    override val route = "music"
}

object NewReleaseDestination : NavigationDestination {
    override val route = "new_release"
}
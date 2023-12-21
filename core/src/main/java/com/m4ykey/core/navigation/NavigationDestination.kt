package com.m4ykey.core.navigation

interface NavigationDestination {
    val route : String
}

object ToolsDestination : NavigationDestination {
    override val route = "tools_destination"
}

object HomeDestination : NavigationDestination {
    override val route = "home_destination"
}

object NewsDestination : NavigationDestination {
    override val route = "news_destination"
}

object NewReleaseDestination : NavigationDestination {
    override val route = "new_release_destination"
}
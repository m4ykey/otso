package com.m4ykey.otso.navigation

interface NavigationDestination {
    val route : String
}

object HomeDestination : NavigationDestination {
    override val route = "home_destination"
}

object NewsDestination : NavigationDestination {
    override val route = "news_destination"
}

object TestDestination : NavigationDestination {
    override val route = "test_dest"
}
package com.m4ykey.otso.navigation

interface NavigationDestination {
    val route : String
}

object HomeDestination : NavigationDestination {
    override val route = "home_destination"
}
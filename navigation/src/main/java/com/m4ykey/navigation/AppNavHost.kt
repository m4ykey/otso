package com.m4ykey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.m4ykey.ui.MusicHomeScreen
import com.m4ykey.ui.NewsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MusicDestination.route
    ) {
        composable(route = MusicDestination.route) {
            MusicHomeScreen(
                onNewReleaseClick = {
                    navController.navigate(NewReleaseDestination.route)
                }
            )
        }
        composable(route = NewsDestination.route) {
            NewsScreen()
        }
    }
}
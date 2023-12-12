package com.m4ykey.otso.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.m4ykey.otso.HomeScreen
import com.m4ykey.ui.NewsScreen

@Composable
fun AppNavHost(
    modifier : Modifier = Modifier,
    navController : NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                onNewsClick = { navController.navigate(NewsDestination.route) }
            )
        }
        composable(route = NewsDestination.route) {
            NewsScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
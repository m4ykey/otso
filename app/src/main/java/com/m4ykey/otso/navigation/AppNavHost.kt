package com.m4ykey.otso.navigation

import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.m4ykey.core.navigation.HomeDestination
import com.m4ykey.core.navigation.NewsDestination
import com.m4ykey.core.navigation.ToolsDestination
import com.m4ykey.otso.HomeScreen
import com.m4ykey.otso.ToolsScreen
import com.m4ykey.ui.NewsScreen

@Composable
fun AppNavHost(
    modifier : Modifier = Modifier,
    navController : NavHostController,
    connectivityManager: ConnectivityManager
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                connectivityManager = connectivityManager,
                onNewsClick = { navController.navigate(NewsDestination.route) }
            )
        }
        composable(route = NewsDestination.route) {
            NewsScreen(
                onNavigateBack = { navController.navigateUp() },
                connectivityManager = connectivityManager
            )
        }
        composable(route = ToolsDestination.route) {
            ToolsScreen()
        }
    }
}
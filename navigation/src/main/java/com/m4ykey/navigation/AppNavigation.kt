package com.m4ykey.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.m4ykey.navigation.bottom_nav.BottomNavigationBar
import com.m4ykey.navigation.bottom_nav.getBottomNavigationItem

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val bottomBarVisible = shouldShowBottomNavigationBar(navBackStackEntry.value?.destination?.route)

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BottomNavigationBar(
                    bottomItems = getBottomNavigationItem(),
                    navController = navController,
                    navBackStackEntry = navBackStackEntry
                )
            }
        }
    ) {
        AppNavHost(
            modifier = modifier.padding(it),
            navController = navController
        )
    }
}

fun shouldShowBottomNavigationBar(currentRoute : String?) : Boolean {
    return currentRoute !in setOf(
        NewReleaseDestination.route
    )
}
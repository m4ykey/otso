package com.m4ykey.otso

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.m4ykey.otso.navigation.AppNavHost
import com.m4ykey.otso.navigation.NewsDestination
import com.m4ykey.otso.navigation.bottom_nav.BottomNavigationBar
import com.m4ykey.otso.navigation.bottom_nav.getBottomNavigationItem

@Composable
fun BaseApp(
    modifier : Modifier = Modifier,
    navController : NavHostController = rememberNavController()
) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val bottomItems = getBottomNavigationItem()

    val bottomBarVisible = shouldShowBottomNavigationBar(navBackStackEntry.value?.destination?.route)

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BottomNavigationBar(
                    bottomItems = bottomItems,
                    navController = navController,
                    navBackStackEntry = navBackStackEntry
                )
            }
        }
    ) {
        AppNavHost(
            navController = navController,
            modifier = modifier.padding(it)
        )
    }
}

fun shouldShowBottomNavigationBar(currentRoute : String?) : Boolean {
    return currentRoute !in setOf(
        NewsDestination.route
    )
}
package com.m4ykey.otso

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.m4ykey.otso.navigation.AppNavHost
import com.m4ykey.otso.navigation.bottom_nav.BottomNavigationBar
import com.m4ykey.otso.navigation.bottom_nav.getBottomNavigationItem

@Composable
fun BaseApp(
    modifier : Modifier = Modifier,
    navController : NavHostController = rememberNavController()
) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val bottomItems = getBottomNavigationItem()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomItems = bottomItems,
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )
        }
    ) {
        AppNavHost(
            navController = navController,
            modifier = modifier.padding(it)
        )
    }
}
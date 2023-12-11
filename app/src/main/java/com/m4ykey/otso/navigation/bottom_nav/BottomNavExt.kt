package com.m4ykey.otso.navigation.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.m4ykey.otso.R
import com.m4ykey.otso.navigation.HomeDestination

@Composable
fun getBottomNavigationItem(): List<BottomNavigationModel> {
    return listOf(
        BottomNavigationModel(
            title = stringResource(id = R.string.home),
            route = HomeDestination.route,
            icon = Icons.Outlined.Home
        )
    )
}

@Composable
fun BottomNavigationBar(
    bottomItems: List<BottomNavigationModel>,
    navController: NavHostController,
    navBackStackEntry: State<NavBackStackEntry?>
) {
    NavigationBar {
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = navBackStackEntry.value?.destination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}
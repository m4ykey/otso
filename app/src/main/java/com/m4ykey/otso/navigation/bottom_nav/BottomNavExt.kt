package com.m4ykey.otso.navigation.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.m4ykey.core.navigation.HomeDestination
import com.m4ykey.core.navigation.ToolsDestination
import com.m4ykey.otso.R

@Composable
fun getBottomNavigationItem(): List<BottomNavigationModel> {
    return listOf(
        BottomNavigationModel(
            title = stringResource(id = R.string.home),
            route = HomeDestination.route,
            unSelectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Rounded.Home
        ),
        BottomNavigationModel(
            title = stringResource(id = R.string.tools),
            route = ToolsDestination.route,
            unSelectedIcon = Icons.Outlined.Build,
            selectedIcon = Icons.Rounded.Build
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
            val isSelected = item.route ==
                    navBackStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unSelectedIcon,
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
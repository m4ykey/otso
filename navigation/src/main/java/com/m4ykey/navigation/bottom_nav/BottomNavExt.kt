package com.m4ykey.navigation.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.rounded.MusicNote
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
import com.m4ykey.navigation.Music
import com.m4ykey.navigation.News
import com.m4ykey.navigation.R
import com.m4ykey.navigation.Tools

@Composable
fun getBottomNavigationItem(): List<BottomNavigationModel> {
    return listOf(
        BottomNavigationModel(
            title = stringResource(id = R.string.music),
            route = Music.MusicDestination.route,
            unSelectedIcon = Icons.Outlined.MusicNote,
            selectedIcon = Icons.Rounded.MusicNote
        ),
        BottomNavigationModel(
            title = stringResource(id = R.string.news),
            route = News.NewsDestination.route,
            unSelectedIcon = Icons.Outlined.Public,
            selectedIcon = Icons.Default.Public
        ),
        BottomNavigationModel(
            title = stringResource(id = R.string.tools),
            route = Tools.ToolsDestination.route,
            unSelectedIcon = Icons.Outlined.Build,
            selectedIcon = Icons.Default.Build
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
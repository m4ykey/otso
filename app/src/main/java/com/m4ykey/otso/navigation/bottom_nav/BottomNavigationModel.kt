package com.m4ykey.otso.navigation.bottom_nav

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationModel(
    val title: String,
    val route: String,
    val unSelectedIcon: ImageVector,
    val selectedIcon : ImageVector
)

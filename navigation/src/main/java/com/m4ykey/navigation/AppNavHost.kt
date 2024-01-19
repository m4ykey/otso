package com.m4ykey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.m4ykey.ui.MusicHomeScreen
import com.m4ykey.ui.NewsScreen
import com.m4ykey.ui.ToolsScreen
import com.m4ykey.ui.spotify.album.AlbumDetailScreen
import com.m4ykey.ui.spotify.album.NewReleaseScreen
import com.m4ykey.ui.spotify.artist.ArtistScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Music.MusicDestination.route
    ) {
        composable(route = News.NewsDestination.route) { NewsScreen() }
        composable(route = Music.NewReleaseDestination.route) {
            NewReleaseScreen(
                onNavigateBack = { navController.navigateUp() },
                onAlbumClick = { navController.navigate("${Music.AlbumDetailDestination.route}/$it") }
            )
        }
        composable(route = Music.MusicDestination.route) {
            MusicHomeScreen(
                onNewReleaseClick = { navController.navigate(Music.NewReleaseDestination.route) },
                onAlbumClick = { navController.navigate("${Music.AlbumDetailDestination.route}/$it") }
            )
        }
        composable(
            route = "${Music.AlbumDetailDestination.route}/{albumId}",
            arguments = listOf(
                navArgument("albumId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val albumId = arguments.getString("albumId", "")
            AlbumDetailScreen(
                id = albumId,
                onNavigateBack = { navController.navigateUp() },
                navigateToArtist = { navController.navigate("${Music.ArtistDestination.route}/$it") }
            )
        }
        composable(
            route = "${Music.ArtistDestination.route}/{artistId}",
            arguments = listOf(
                navArgument("artistId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val artistId = arguments.getString("artistId", "")
            ArtistScreen(
                artistId = artistId,
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(route = Tools.ToolsDestination.route) { ToolsScreen() }
    }
}
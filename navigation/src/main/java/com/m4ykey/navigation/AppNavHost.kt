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
import com.m4ykey.ui.lyrics.LyricsScreen
import com.m4ykey.ui.search.SearchScreen
import com.m4ykey.ui.spotify.album.AlbumDetailScreen
import com.m4ykey.ui.spotify.album.NewReleaseScreen
import com.m4ykey.ui.spotify.playlist.FeaturedPlaylistScreen

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
                onAlbumClick = { navController.navigate("${Music.AlbumDetailDestination.route}/$it") },
                onFeaturedPlaylistClick = { navController.navigate(Music.PlaylistDestination.route) },
                onSearchClick = { navController.navigate(Music.SearchDestination.route) }
            )
        }
        composable(
            route = "${Music.AlbumDetailDestination.route}/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val albumId = arguments.getString("albumId", "")
            AlbumDetailScreen(
                id = albumId,
                onNavigateBack = { navController.navigateUp() },
                onTrackClick = { name, artist, image ->
                    navController.navigate("${Music.LyricsDestination.route}/$name/$artist}/$image}")
                }
            )
        }
        composable(
            route = "${Music.LyricsDestination.route}/{name}/{artist}/{image}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("artist") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val args = requireNotNull(backStackEntry.arguments)
            val name = args.getString("name", "")
            val artist = args.getString("artist", "")
            val image = args.getString("image", "")
            LyricsScreen(
                artist = artist,
                track = name,
                image = image,
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(route = Music.PlaylistDestination.route) {
            FeaturedPlaylistScreen(onNavigateBack = { navController.navigateUp() })
        }
        composable(route = Music.SearchDestination.route) {
            SearchScreen()
        }
        composable(route = Tools.ToolsDestination.route) { ToolsScreen() }
    }
}
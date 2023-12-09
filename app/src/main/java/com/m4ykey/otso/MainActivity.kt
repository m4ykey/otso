package com.m4ykey.otso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.m4ykey.otso.navigation.bottom_nav.BottomNavigationBar
import com.m4ykey.otso.navigation.bottom_nav.getBottomNavigationItem
import com.m4ykey.otso.ui.theme.OtsoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OtsoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestApp()
                }
            }
        }
    }
}

@Composable
fun TestApp(navController : NavHostController = rememberNavController()) {

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
        Column(modifier = Modifier.padding(it)) {

        }
    }

}
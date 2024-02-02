package com.m4ykey.otso

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.m4ykey.navigation.AppNavigation
import com.m4ykey.otso.notification.StartServiceReceiver
import com.m4ykey.otso.notification.showNotification
import com.m4ykey.otso.ui.theme.OtsoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OtsoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()
                    AppNavigation(navController = navController)

                    showNotification(this)
                    val intent = Intent(this, StartServiceReceiver::class.java)
                    intent.action = "custom.start.service.action"
                    sendBroadcast(intent)
                }
            }
        }
    }
}
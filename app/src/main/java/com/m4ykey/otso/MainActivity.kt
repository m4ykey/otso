package com.m4ykey.otso

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.messaging.FirebaseMessaging
import com.m4ykey.navigation.AppNavigation
import com.m4ykey.otso.notification.FirebaseMessagingDialog
import com.m4ykey.otso.ui.theme.OtsoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    val navController : NavHostController = rememberNavController()
                    AppNavigation(navController = navController)

                    firebaseNotification()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun firebaseNotification() {

    val FCM = "FCM"

    val showNotificationDialog = remember {
        mutableStateOf(false)
    }

    val notificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (showNotificationDialog.value) {
        FirebaseMessagingDialog(
            notificationPermission = notificationPermission,
            showNotificationDialog = showNotificationDialog
        )
    } else {

    }

    LaunchedEffect(Unit) {
        if (notificationPermission.status.isGranted || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {

        } else {
            showNotificationDialog.value = true
        }
    }

    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.i(FCM, "Firebase Messaging token failed", task.exception)
            return@addOnCompleteListener
        }
        Log.i(FCM, task.result)
    }
}
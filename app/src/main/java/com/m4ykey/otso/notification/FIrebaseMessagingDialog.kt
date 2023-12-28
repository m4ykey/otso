package com.m4ykey.otso.notification

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FirebaseMessagingDialog(
    notificationPermission : PermissionState,
    showNotificationDialog : MutableState<Boolean>
) {
    if (showNotificationDialog.value) {
        AlertDialog(
            onDismissRequest = {
                notificationPermission.launchPermissionRequest()
                showNotificationDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        notificationPermission.launchPermissionRequest()
                        showNotificationDialog.value = false
                    }
                ) {
                    Text(text = "Ok")
                }
            },
            title = { Text(text = "Notification Permission") },
            text = {
                Text(text = "Allow permission to get a notifications")
            }
        )
    }
}
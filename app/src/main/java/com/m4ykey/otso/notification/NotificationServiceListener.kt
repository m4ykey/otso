package com.m4ykey.otso.notification

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class NotificationServiceListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification
        val title = notification.extras.getCharSequence("android.title")?.toString()
        val text = notification.extras.getCharSequence("android.text")?.toString()

        Log.d(TAG, "Received notification: $title - $text")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d(TAG, "Removed notification: ${sbn.notification}")
    }

    companion object {
        const val TAG = "NotificationListener"
    }

}

fun openNotificationAccessSettings(context : Context) {
    context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
}

fun isNotificationListenerEnabled(context : Context) : Boolean {
    val flat = Settings.Secure.getString(
        context.contentResolver,
        "enabled_notification_listeners"
    )
    return flat?.contains(context.packageName) == true
}

fun checkNotificationListenerPermission(context : Context) {
    val permission = Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE

    if (!isNotificationListenerEnabled(context)) {
        openNotificationAccessSettings(context)
    } else {
        val isPermissionGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        if (!isPermissionGranted) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(permission),
                1
            )
        }
    }
}

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
import com.m4ykey.core.helpers.MusicNotificationState

class NotificationServiceListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification
        val packageName = sbn.packageName

        if (isMusicApp(packageName)) {
            val title = notification.extras.getCharSequence("android.title")?.toString()
            val artist = notification.extras.getCharSequence("android.text")?.toString()
            val subText = notification.extras.getCharSequence("android.subText")?.toString()
            val infoText = notification.extras.getCharSequence("android.infoText")?.toString()

            val songInfo = listOfNotNull(title, artist, subText, infoText).joinToString(" - ")

            MusicNotificationState.updateArtist(artist.orEmpty())
            MusicNotificationState.updateTitle(title.orEmpty())
            Log.d(TAG, "Received notification: $songInfo")
        }
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

private fun isMusicApp(packageName : String) : Boolean {
    return packageName in listOf(
        "com.spotify.music",
        "com.google.android.apps.youtube.music",
        "deezer.package.name",
        "com.soundcloud.android",
        "com.aspiro.tidal"
    )
}
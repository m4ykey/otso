package com.m4ykey.otso.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.m4ykey.core.notification.MusicNotificationState

class NotificationServiceListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val extras = sbn.notification.extras

        if (isMusicApp(packageName)) {
            val title = extras.getCharSequence("android.title")?.toString()
            val artist = extras.getCharSequence("android.text")?.toString()

            val songInfo = listOfNotNull(title, artist).joinToString(" - ")

            MusicNotificationState.updateArtist(artist.orEmpty())
            MusicNotificationState.updateTitle(title.orEmpty())

            Log.d(TAG, "Received notification: $songInfo")
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
        Log.d(TAG, "Removed notification: ${sbn.notification}")
    }

    companion object {
        const val TAG = "NotificationListener"
    }

}

private fun isMusicApp(packageName : String) : Boolean {
    return packageName in listOf(
        "com.spotify.music",
        "com.google.android.apps.youtube.music",
        "deezer.package.name",
        "com.soundcloud.android",
        "com.aspiro.tidal",
        "com.shazam.android"
    )
}
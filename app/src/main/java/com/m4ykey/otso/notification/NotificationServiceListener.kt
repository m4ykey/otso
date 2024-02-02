package com.m4ykey.otso.notification

import android.content.pm.ApplicationInfo
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import com.m4ykey.core.notification.MusicNotificationState

class NotificationServiceListener : NotificationListenerService() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val extras = sbn.notification.extras
        val packageName = sbn.packageName
        val keys = extras.keySet()

        if (isMusicApp(packageName)) {
            val title = extras.getCharSequence("android.title", "")?.toString()
            val artist = extras.getCharSequence("android.text", "")?.toString()
            val applicationInfo = extras.getParcelable("android.appInfo") as ApplicationInfo?
            val appInfo = applicationInfo?.packageName
            val subText = extras.getCharSequence("android.subText", "")?.toString()

            val songInfo = listOfNotNull(title, artist, appInfo).joinToString(" - ")

            MusicNotificationState.updateArtist(artist.orEmpty())
            MusicNotificationState.updateTitle(title.orEmpty())
            MusicNotificationState.updateAppInfo(appInfo.orEmpty())
            MusicNotificationState.updateSubText(subText.orEmpty())

            for (key in keys) {
                val value = extras.get(key)
                Log.d(TAG, "Key: $key, Value: $value")
            }

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
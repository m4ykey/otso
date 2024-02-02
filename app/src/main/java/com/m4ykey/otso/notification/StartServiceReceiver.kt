package com.m4ykey.otso.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StartServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "custom.start.service.action") {
            val serviceIntent = Intent(context, NotificationServiceListener::class.java)
            context?.startService(serviceIntent)
        }
    }
}
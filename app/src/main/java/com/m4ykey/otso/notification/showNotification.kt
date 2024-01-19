package com.m4ykey.otso.notification

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun showNotification(context : Context) {

    val REQUEST_NOTIFICATION = 1
    val TAG = "FCM"
    val permission = Manifest.permission.POST_NOTIFICATIONS

    val isPermissionGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    if (!isPermissionGranted) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(permission),
            REQUEST_NOTIFICATION
        )
    } else {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.i(TAG, "Firebase Messaging token: $token")
            } else {
                Log.i(TAG, "Firebase Messaging token failed", task.exception)
            }
        }
    }
}
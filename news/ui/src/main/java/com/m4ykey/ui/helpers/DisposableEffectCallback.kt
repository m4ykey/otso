package com.m4ykey.ui.helpers

import android.content.Context
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher

class DisposableEffectCallback {
    var launcher : ActivityResultLauncher<String>? = null

    fun onOpenUrlResult(context : Context) {
        launcher?.let {
            Toast.makeText(context, "Failed to open URL", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.m4ykey.ui.helpers

import android.content.Context
import android.content.Intent

fun shareUrl(context : Context, url : String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.apply {
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val chooser = Intent.createChooser(intent, url)
    context.startActivity(chooser)
}
package com.m4ykey.core.urls

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openUrl(context : Context, url: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun shareUrl(context : Context, url : String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.apply {
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val chooser = Intent.createChooser(intent, url)
    context.startActivity(chooser)
}
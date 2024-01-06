package com.m4ykey.core.urls

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openUrl(context : Context, url: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}
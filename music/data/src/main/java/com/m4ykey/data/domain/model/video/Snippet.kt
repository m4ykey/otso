package com.m4ykey.data.domain.model.video

import androidx.compose.runtime.Immutable

@Immutable
data class Snippet(
    val thumbnails: Thumbnails? = null,
    val title: String? = ""
)
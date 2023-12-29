package com.m4ykey.data.domain.model.video

import androidx.compose.runtime.Immutable

@Immutable
data class Maxres(
    val height: Int,
    val url: String,
    val width: Int
)

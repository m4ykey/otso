package com.m4ykey.data.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Image(
    val height: Int,
    val url: String,
    val width: Int
)

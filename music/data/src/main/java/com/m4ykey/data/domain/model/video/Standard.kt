package com.m4ykey.data.domain.model.video

import androidx.compose.runtime.Immutable

@Immutable
data class Standard(
    val height: Int? = 0,
    val url: String? = "",
    val width: Int? = 0
)

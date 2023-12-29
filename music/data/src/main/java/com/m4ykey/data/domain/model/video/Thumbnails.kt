package com.m4ykey.data.domain.model.video

import androidx.compose.runtime.Immutable

@Immutable
data class Thumbnails(
    val default: Default,
    val high: High,
    val maxres: Maxres,
    val medium: Medium,
    val standard: Standard
)

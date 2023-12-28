package com.m4ykey.data.domain.model.album

import androidx.compose.runtime.Immutable

@Immutable
data class Copyright(
    val text: String,
    val type: String
)

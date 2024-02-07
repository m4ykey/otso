package com.m4ykey.data.domain.model.album

import androidx.compose.runtime.Immutable

@Immutable
data class Albums(
    val items : List<Items>,
    val next : String,
    val previous : String
)

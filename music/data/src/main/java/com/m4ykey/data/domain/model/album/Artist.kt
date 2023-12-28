package com.m4ykey.data.domain.model.album

import androidx.compose.runtime.Immutable

@Immutable
data class Artist(
    val externalUrls: ExternalUrls? = null,
    val id: String,
    val name: String
)

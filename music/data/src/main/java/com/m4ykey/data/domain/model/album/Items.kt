package com.m4ykey.data.domain.model.album

import androidx.compose.runtime.Immutable

@Immutable
data class Items(
    val albumType: String? = "",
    val artists: List<Artist>,
    val externalUrls: ExternalUrls? = null,
    val id: String,
    val images: List<Image>,
    val name: String,
    val releaseDate: String? = "",
    val totalTracks: Int? = 0
)
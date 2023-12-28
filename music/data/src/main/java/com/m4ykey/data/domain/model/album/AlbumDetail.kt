package com.m4ykey.data.domain.model.album

import androidx.compose.runtime.Immutable

@Immutable
data class AlbumDetail(
    val albumType : String,
    val artists : List<Artist>,
    val copyrights : List<Copyright>,
    val externalUrls: ExternalUrls? = null,
    val id : String,
    val images : List<Image>,
    val label : String,
    val name : String,
    val popularity : Int,
    val releaseDate : String,
    val totalTracks : Int
)

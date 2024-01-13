package com.m4ykey.data.domain.model.artist

import com.m4ykey.data.domain.model.album.ExternalUrls
import com.m4ykey.data.domain.model.album.Image

data class Artist(
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val images : List<Image>,
    val genres: List<String>,
    val href: String,
    val id: String,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)

package com.m4ykey.data.domain.model.artist

import com.m4ykey.data.domain.model.album.ExternalUrls
import com.m4ykey.data.domain.model.album.Image

data class Artist(
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val genres : List<String>,
    val id : String,
    val images : List<Image>,
    val name : String,
    val popularity : Int
)

package com.m4ykey.data.remote.model.artist

import com.m4ykey.data.remote.model.album.ExternalUrlsDto
import com.m4ykey.data.remote.model.album.ImageDto

data class ArtistDto(
    val external_urls: ExternalUrlsDto,
    val followers: FollowersDto,
    val genres: List<String>,
    val id: String,
    val images: List<ImageDto>,
    val name: String,
    val popularity: Int
)
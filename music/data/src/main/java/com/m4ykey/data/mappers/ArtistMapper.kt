package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.artist.Artist
import com.m4ykey.data.domain.model.artist.Followers
import com.m4ykey.data.remote.model.artist.ArtistDto
import com.m4ykey.data.remote.model.artist.FollowersDto

fun FollowersDto.toFollowers() : Followers = Followers(total = total)

fun ArtistDto.toArtist() : Artist {
    return Artist(
        externalUrls = external_urls.toExternalUrls(),
        followers = followers.toFollowers(),
        genres = genres,
        id = id,
        images = images.map { it.toImage() },
        name = name,
        popularity = popularity
    )
}
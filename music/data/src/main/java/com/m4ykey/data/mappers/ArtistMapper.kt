package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.artist.Artist
import com.m4ykey.data.domain.model.artist.Followers
import com.m4ykey.data.remote.model.artist.ArtistDto
import com.m4ykey.data.remote.model.artist.FollowersDto

fun ArtistDto.toArtist() : Artist {
    return Artist(
        externalUrls = external_urls.toExternalUrls(),
        genres = genres,
        href = href,
        id = id,
        name = name,
        popularity = popularity,
        type = type,
        uri = uri,
        images = images.map { it.toImage() },
        followers = followers.toFollowers()
    )
}

fun FollowersDto.toFollowers() : Followers = Followers(total = total)
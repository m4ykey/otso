package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.model.track.Track
import com.m4ykey.data.remote.model.album.ItemsDto
import com.m4ykey.data.remote.model.track.TrackDto

fun TrackDto.toTrack() : Track {
    return Track(
        album = album.toAlbum()
    )
}

fun ItemsDto.toAlbum() : Items {
    return Items(
        id = id,
        name = name,
        artists = artists.map { it.toArtist() },
        images = images.map { it.toImage() }
    )
}
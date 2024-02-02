package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.playlist.PlaylistImage
import com.m4ykey.data.domain.model.playlist.PlaylistItems
import com.m4ykey.data.domain.model.playlist.Playlists
import com.m4ykey.data.remote.model.playlist.PlaylistImageDto
import com.m4ykey.data.remote.model.playlist.PlaylistItemsDto
import com.m4ykey.data.remote.model.playlist.PlaylistsDto

fun PlaylistItemsDto.toPlaylistItems() : PlaylistItems {
    return PlaylistItems(
        description = description,
        id = id,
        name = name,
        externalUrls = external_urls.toExternalUrls(),
        images = images.map { it.toPlaylistImage() },
        primaryColor = primary_color
    )
}

fun PlaylistsDto.toPlaylists() : Playlists {
    return Playlists(
        previous = previous ?: "",
        next = next ?: "",
        items = items.map { it.toPlaylistItems() }
    )
}

fun PlaylistImageDto.toPlaylistImage() : PlaylistImage {
    return PlaylistImage(
        height = height ?: "",
        width = width ?: "",
        url = url
    )
}
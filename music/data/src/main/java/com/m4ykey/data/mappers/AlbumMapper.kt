package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.album.AlbumDetail
import com.m4ykey.data.domain.model.album.Albums
import com.m4ykey.data.domain.model.album.Artist
import com.m4ykey.data.domain.model.album.Copyright
import com.m4ykey.data.domain.model.album.ExternalUrls
import com.m4ykey.data.domain.model.album.Image
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.remote.model.album.AlbumDetailDto
import com.m4ykey.data.remote.model.album.AlbumsDto
import com.m4ykey.data.remote.model.album.ArtistDto
import com.m4ykey.data.remote.model.album.CopyrightDto
import com.m4ykey.data.remote.model.album.ExternalUrlsDto
import com.m4ykey.data.remote.model.album.ImageDto
import com.m4ykey.data.remote.model.album.ItemsDto

fun ArtistDto.toArtist() : Artist {
    return Artist(
        id = id ?: "",
        name = name ?: "",
        externalUrls = externalUrls?.toExternalUrls()
    )
}

fun ExternalUrlsDto.toExternalUrls() : ExternalUrls = ExternalUrls(spotify = spotify ?: "")

fun ImageDto.toImage() : Image {
    return Image(
        height = height ?: 0,
        width = width ?: 0,
        url = url ?: ""
    )
}

fun ItemsDto.toItems() : Items {
    return Items(
        id = id ?: "",
        albumType = album_type ?: "",
        name = name ?: "",
        releaseDate = release_date ?: "",
        totalTracks = total_tracks ?: 0,
        artists = artists.map { it.toArtist() },
        externalUrls = external_urls.toExternalUrls(),
        images = images.map { it.toImage() }
    )
}

fun AlbumsDto.toAlbums() : Albums = Albums(items = items.map { it.toItems() })

fun AlbumDetailDto.toAlbumDetail() : AlbumDetail {
    return AlbumDetail(
        albumType = album_type,
        id = id,
        label = label,
        name = name,
        popularity = popularity,
        releaseDate = release_date,
        totalTracks = total_tracks,
        externalUrls = external_urls.toExternalUrls(),
        artists = artists.map { it.toArtist() },
        images = images.map { it.toImage() },
        copyrights = copyrights.map { it.toCopyright() }
    )
}

fun CopyrightDto.toCopyright() : Copyright {
    return Copyright(
        text = text,
        type = type
    )
}
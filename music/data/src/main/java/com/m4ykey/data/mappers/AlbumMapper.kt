package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.Albums
import com.m4ykey.data.domain.model.Artist
import com.m4ykey.data.domain.model.ExternalUrls
import com.m4ykey.data.domain.model.Image
import com.m4ykey.data.domain.model.Items
import com.m4ykey.data.local.ArtistEntity
import com.m4ykey.data.local.ImageEntity
import com.m4ykey.data.local.NewReleaseEntity
import com.m4ykey.data.remote.album.model.AlbumsDto
import com.m4ykey.data.remote.album.model.ArtistDto
import com.m4ykey.data.remote.album.model.ExternalUrlsDto
import com.m4ykey.data.remote.album.model.ImageDto
import com.m4ykey.data.remote.album.model.ItemsDto

fun ArtistDto.toArtist() : Artist {
    return Artist(
        id = id ?: "",
        name = name ?: "",
        externalUrls = ExternalUrls(spotify = externalUrls?.spotify ?: "")
    )
}

fun ExternalUrlsDto.toExternalUrls() : ExternalUrls {
    return ExternalUrls(
        spotify = spotify ?: ""
    )
}

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

fun AlbumsDto.toAlbums() : Albums {
    return Albums(
        items = items.map { it.toItems() }
    )
}

fun NewReleaseEntity.toNewRelease() : Items {
    return Items(
        name = name,
        images = listOf(images.toImage()),
        artists = artists.map(ArtistEntity::toArtist),
        id = id
    )
}

fun ImageEntity.toImage() : Image {
    return Image(
        height = height,
        url = url,
        width = width
    )
}

fun ArtistEntity.toArtist() : Artist {
    return Artist(
        name = name
    )
}

fun Items.toNewReleaseEntity() : NewReleaseEntity {
    return NewReleaseEntity(
        id = id,
        name = name,
        artists = artists.map(Artist::toArtistEntity),
        images = images.firstOrNull()?.toImageEntity() ?: ImageEntity(
            height = 0,
            width = 0,
            url = ""
        )
    )
}

fun Artist.toArtistEntity() : ArtistEntity {
    return ArtistEntity(
        name = name
    )
}

fun Image.toImageEntity() : ImageEntity {
    return ImageEntity(
        height = height,
        url = url,
        width = width
    )
}
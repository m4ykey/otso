package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.album.AlbumDetail
import com.m4ykey.data.domain.model.album.Albums
import com.m4ykey.data.domain.model.album.Artist
import com.m4ykey.data.domain.model.album.Copyright
import com.m4ykey.data.domain.model.album.ExternalUrls
import com.m4ykey.data.domain.model.album.Image
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.data.remote.model.album.AlbumDetailDto
import com.m4ykey.data.remote.model.album.AlbumsDto
import com.m4ykey.data.remote.model.album.ArtistDto
import com.m4ykey.data.remote.model.album.CopyrightDto
import com.m4ykey.data.remote.model.album.ExternalUrlsDto
import com.m4ykey.data.remote.model.album.ImageDto
import com.m4ykey.data.remote.model.album.ItemsDto
import com.m4ykey.data.remote.model.album.tracks.TrackItemDto

fun ArtistDto.toArtist() : Artist {
    return Artist(
        id = id,
        name = name,
        externalUrls = externalUrls?.toExternalUrls()
    )
}

fun ExternalUrlsDto.toExternalUrls() : ExternalUrls = ExternalUrls(spotify = spotify)

fun ImageDto.toImage() : Image {
    return Image(
        height = height,
        width = width,
        url = url
    )
}

fun ItemsDto.toItems() : Items {
    return Items(
        id = id,
        albumType = album_type,
        name = name,
        releaseDate = release_date,
        totalTracks = total_tracks,
        artists = artists.map { it.toArtist() },
        externalUrls = external_urls.toExternalUrls(),
        images = images.map { it.toImage() }
    )
}

fun AlbumsDto.toAlbums() : Albums = Albums(
    items = items.map { it.toItems() },
    next = next ?: "",
    previous = previous ?: ""
)

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

fun TrackItemDto.toTrackItem() : TrackItem {
    return TrackItem(
        discNumber = disc_number,
        durationMs = duration_ms,
        id = id,
        name = name,
        previewUrl = preview_url ?: "",
        artists = artists.map { it.toArtist() },
        explicit = explicit,
        externalUrls = external_urls.toExternalUrls(),
        trackNumber = track_number
    )
}
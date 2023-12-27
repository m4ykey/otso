package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.video.Snippet
import com.m4ykey.data.domain.model.video.Standard
import com.m4ykey.data.domain.model.video.Thumbnails
import com.m4ykey.data.domain.model.video.VideoItem
import com.m4ykey.data.remote.model.youtube.SnippetDto
import com.m4ykey.data.remote.model.youtube.StandardDto
import com.m4ykey.data.remote.model.youtube.ThumbnailsDto
import com.m4ykey.data.remote.model.youtube.VideoItemDto

fun StandardDto.toStandard() : Standard {
    return Standard(
        height = height,
        url = url,
        width = width
    )
}

fun ThumbnailsDto.toThumbnails() : Thumbnails {
    return Thumbnails(
        standard = standard.toStandard()
    )
}

fun SnippetDto.toSnippet() : Snippet {
    return Snippet(
        channelId = channelId,
        channelTitle = channelTitle,
        title = title,
        thumbnails = thumbnails.toThumbnails()
    )
}

fun VideoItemDto.toVideoItem() : VideoItem {
    return VideoItem(
        id = id,
        snippet = snippet.toSnippet()
    )
}
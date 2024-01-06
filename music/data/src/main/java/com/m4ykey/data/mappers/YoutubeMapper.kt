package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.video.Snippet
import com.m4ykey.data.domain.model.video.Standard
import com.m4ykey.data.domain.model.video.Thumbnails
import com.m4ykey.data.domain.model.video.VideoItem
import com.m4ykey.data.remote.model.youtube.SnippetDto
import com.m4ykey.data.remote.model.youtube.StandardDto
import com.m4ykey.data.remote.model.youtube.ThumbnailsDto
import com.m4ykey.data.remote.model.youtube.VideoItemDto

fun VideoItemDto.toVideoItem() : VideoItem = VideoItem(id = id, snippet = snippet.toSnippet())

fun SnippetDto.toSnippet() : Snippet = Snippet(
    categoryId = categoryId,
    channelId = channelId,
    channelTitle = channelTitle,
    thumbnails = thumbnails.toThumbnails(),
    title = title
)

fun ThumbnailsDto.toThumbnails() : Thumbnails = Thumbnails(standard = standard.toStandard())

fun StandardDto.toStandard() : Standard = Standard(height = height, url = url, width = width)
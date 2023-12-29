package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.video.Default
import com.m4ykey.data.domain.model.video.High
import com.m4ykey.data.domain.model.video.Maxres
import com.m4ykey.data.domain.model.video.Medium
import com.m4ykey.data.domain.model.video.Snippet
import com.m4ykey.data.domain.model.video.Standard
import com.m4ykey.data.domain.model.video.Thumbnails
import com.m4ykey.data.domain.model.video.VideoItem
import com.m4ykey.data.remote.model.youtube.DefaultDto
import com.m4ykey.data.remote.model.youtube.HighDto
import com.m4ykey.data.remote.model.youtube.MaxresDto
import com.m4ykey.data.remote.model.youtube.MediumDto
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

fun ThumbnailsDto.toThumbnails() : Thumbnails = Thumbnails(
    default = default.toDefault(),
    high = high.toHigh(),
    maxres = maxres.toMaxres(),
    medium = medium.toMedium(),
    standard = standard.toStandard()
)

fun DefaultDto.toDefault() : Default = Default(
    height = height, url = url, width = width
)

fun HighDto.toHigh() : High = High(
    height = height, url = url, width = width
)

fun MaxresDto.toMaxres() : Maxres = Maxres(
    height = height, url = url, width = width
)

fun MediumDto.toMedium() : Medium = Medium(
    height = height, url = url, width = width
)

fun StandardDto.toStandard() : Standard = Standard(
    height = height, url = url, width = width
)
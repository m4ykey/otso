package com.m4ykey.data.mappers

import com.m4ykey.data.domain.model.video.VideoItem
import com.m4ykey.data.remote.model.youtube.VideoItemDto

fun VideoItemDto.toVideoItem() : VideoItem {
    return VideoItem(
        id = id
    )
}
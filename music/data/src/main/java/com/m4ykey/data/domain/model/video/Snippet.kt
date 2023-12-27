package com.m4ykey.data.domain.model.video

data class Snippet(
    val channelId : String,
    val channelTitle : String,
    val thumbnails: Thumbnails,
    val title : String
)
package com.m4ykey.data.remote.model.track

data class TracksDto(
    val href: String,
    val items: List<TrackDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)
package com.m4ykey.data.remote.model.album.tracks

data class TracksListDto(
    val items: List<TrackItemDto>,
    val limit : Int? = 0,
    val next : String? = "",
    val offset : Int? = 0,
    val previous : String? = ""
)
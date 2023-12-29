package com.m4ykey.data.remote.model.youtube

data class ThumbnailsDto(
    val default: DefaultDto,
    val high: HighDto,
    val maxres: MaxresDto,
    val medium: MediumDto,
    val standard: StandardDto
)
package com.m4ykey.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceDto(
    val name: String? = ""
)
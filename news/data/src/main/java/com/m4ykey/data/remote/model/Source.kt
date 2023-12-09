package com.m4ykey.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(
    val id: String,
    val name: String
)
package com.m4ykey.remote.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(
    val id: String = "",
    val name: String = ""
)
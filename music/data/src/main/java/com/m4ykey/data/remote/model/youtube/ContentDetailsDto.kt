package com.m4ykey.data.remote.model.youtube

data class ContentDetailsDto(
    val caption: String? = "",
    val definition: String? = "",
    val dimension: String? = "",
    val duration: String? = "",
    val licensedContent: Boolean? = false,
    val projection: String? = ""
)
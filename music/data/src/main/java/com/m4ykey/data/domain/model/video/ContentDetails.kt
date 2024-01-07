package com.m4ykey.data.domain.model.video

data class ContentDetails(
    val caption: String? = "",
    val definition: String? = "",
    val dimension: String? = "",
    val duration: String? = "",
    val licensedContent: Boolean? = false,
    val projection: String? = ""
)

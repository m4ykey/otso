package com.m4ykey.data.domain.model.album

data class Albums(
    val items : List<Items>,
    val next : String,
    val previous : String
)

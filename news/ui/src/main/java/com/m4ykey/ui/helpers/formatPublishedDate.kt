package com.m4ykey.ui.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatPublishedDate(date : String) : String {
    val inputDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputDate = DateTimeFormatter.ofPattern("dd MMM yyyy")

    val parsedDate = LocalDateTime.parse(date, inputDate)
    return outputDate.format(parsedDate)
}
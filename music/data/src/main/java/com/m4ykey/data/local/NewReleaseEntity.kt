package com.m4ykey.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.m4ykey.core.Constants

@Entity(tableName = Constants.NEW_RELEASE_TABLE)
data class NewReleaseEntity(
    val artists: List<ArtistEntity>,
    @PrimaryKey(autoGenerate = false) val id: String,
    val images: ImageEntity,
    val name: String
)

data class ArtistEntity(
    val name: String
)

data class ImageEntity(
    val height: Int,
    val url: String,
    val width: Int
)
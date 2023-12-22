package com.m4ykey.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.m4ykey.data.local.ArtistEntity
import com.m4ykey.data.local.ImageEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class AlbumConverter(private val moshi: Moshi) {

    @TypeConverter
    fun fromArtistEntity(json : String) : List<ArtistEntity> {
        val type = Types.newParameterizedType(List::class.java, ArtistEntity::class.java)
        return moshi.adapter<List<ArtistEntity>>(type).fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun toArtistEntity(data : List<ArtistEntity>) : String {
        return moshi.adapter<List<ArtistEntity>>(
            Types.newParameterizedType(List::class.java, ArtistEntity::class.java)
        ).toJson(data)
    }

    @TypeConverter
    fun fromImageEntity(value : ImageEntity) : String {
        val adapter : JsonAdapter<ImageEntity> = moshi.adapter(ImageEntity::class.java)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toImageEntity(value : String) : ImageEntity {
        val adapter : JsonAdapter<ImageEntity> = moshi.adapter(ImageEntity::class.java)
        return adapter.fromJson(value) ?: ImageEntity(height = 0, width = 0, url = "")
    }

}
package com.m4ykey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.m4ykey.data.local.converter.AlbumConverter

@Database(
    entities = [NewReleaseEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AlbumConverter::class)
abstract class MusicDatabase : RoomDatabase() {

    abstract val newReleaseDao : NewReleaseDao
}
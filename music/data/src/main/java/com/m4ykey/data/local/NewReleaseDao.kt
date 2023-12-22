package com.m4ykey.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewReleaseDao {

    @Query("SELECT * FROM new_release_table")
    fun getAllNewReleases() : List<NewReleaseEntity>

    @Query("DELETE FROM new_release_table")
    suspend fun deleteAllNewReleases()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNewReleases(newReleases : List<NewReleaseEntity>)

}
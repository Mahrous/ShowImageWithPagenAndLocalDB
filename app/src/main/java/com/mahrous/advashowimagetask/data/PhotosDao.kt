package com.mahrous.advashowimagetask.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<Photo>)

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): PagingSource<Int, Photo>

    @Query("DELETE FROM photos")
    suspend fun clearPhotos()

}
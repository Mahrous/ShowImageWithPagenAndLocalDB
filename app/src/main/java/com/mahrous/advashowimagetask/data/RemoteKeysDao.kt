package com.mahrous.advashowimagetask.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<PhotosRemoteKey>)

    @Query("SELECT * FROM photosremotekey WHERE photosId = :id")
    suspend fun remoteKeysPhotoId(id: String): PhotosRemoteKey?

    @Query("DELETE FROM photosremotekey")
    suspend fun clearRemoteKeys()
}
package com.mahrous.advashowimagetask.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PhotosRemoteKey::class, Photo::class], version = 1
)
abstract class PhotosDataBase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: PhotosDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PhotosDataBase::class.java, "photos.db"
            )
                .build()
    }
}
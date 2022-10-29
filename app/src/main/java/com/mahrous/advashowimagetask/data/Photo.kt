package com.mahrous.advashowimagetask.data


import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class Photo(
    @field:SerializedName("url")
    var url: String?,
    @field:SerializedName("thumbnailUrl")
    val thumbUrl: String?,

    @PrimaryKey
    @Nullable
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("albumId")
    val albumId: String,


    @field:SerializedName("title")
    val title: String?,
)

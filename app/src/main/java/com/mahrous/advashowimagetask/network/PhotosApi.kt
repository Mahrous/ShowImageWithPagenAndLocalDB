package com.mahrous.advashowimagetask.network

import com.mahrous.advashowimagetask.data.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("_page") page: Int,
        @Query("_limit") perPage: Int
    ): Response<List<Photo>>

}

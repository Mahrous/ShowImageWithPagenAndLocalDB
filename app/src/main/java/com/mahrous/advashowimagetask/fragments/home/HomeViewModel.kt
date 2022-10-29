package com.mahrous.advashowimagetask.fragments.home

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mahrous.advashowimagetask.data.PhotosDataBase
import com.mahrous.advashowimagetask.network.PhotosApi
import com.mahrous.advashowimagetask.paging.PhotoDataSource


class HomeViewModel(
    private val photosApi: PhotosApi, private val db: PhotosDataBase

) : ViewModel() {

    private val pagingSourceFactory = { db.photosDao().getAllPhotos() }


    @ExperimentalPagingApi
    val pager = Pager(
        PagingConfig(pageSize = 20),
        remoteMediator = PhotoDataSource(photosApi, 1, db),
        pagingSourceFactory = pagingSourceFactory

    ).flow
}



package com.mahrous.advashowimagetask.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mahrous.advashowimagetask.data.Photo
import com.mahrous.advashowimagetask.data.PhotosDataBase
import com.mahrous.advashowimagetask.data.PhotosRemoteKey
import com.mahrous.advashowimagetask.network.PhotosApi
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
@OptIn(ExperimentalPagingApi::class)
class PhotoDataSource(
    private val api: PhotosApi,
    private val pageNumber: Int = 1,
    private val db: PhotosDataBase
) : RemoteMediator<Int, Photo>() {

    private val keysDao = db.remoteKeysDao()
    private val photosDao = db.photosDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Photo>
    ): MediatorResult {
        try {

            val page = when (loadType) {
                LoadType.APPEND -> {

                    val remoteKey =
                        getLastRemoteKey(state) ?: throw InvalidObjectException("invalid")
                    remoteKey.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                }


                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getClosestRemoteKeys(state)
                    remoteKey?.nextKey?.minus(1) ?: pageNumber
                }

            }


            val apiResponse = api.getPhotos(
                page,
                state.config.pageSize
            )

            val endPaginatedReached =
                apiResponse.body()!!.size < state.config.pageSize


            if (apiResponse.isSuccessful) {
                apiResponse.body().let {
                    if (loadType == LoadType.REFRESH) {
                        photosDao.clearPhotos()
                        keysDao.clearRemoteKeys()
                    }

                    val preKey = if (page == pageNumber) null else page - 1
                    val nextKey = if (endPaginatedReached) null else page + 1
                    val keysResult = it?.map { photo ->
                        photo.url = photo.url + ".jpg"

                        PhotosRemoteKey(photo.id, prevKey = preKey, nextKey = nextKey)
                    }

                    photosDao.insertAll(it!!)
                    if (keysResult != null) {
                        keysDao.insertRemoteKeys(keysResult)

                    }

                }
                return MediatorResult.Success(endPaginatedReached)
            } else
                return MediatorResult.Success(endOfPaginationReached = true)

        } catch (exception: IOException) {

            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    private suspend fun getClosestRemoteKeys(state: PagingState<Int, Photo>): PhotosRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let { photo ->
                keysDao.remoteKeysPhotoId(photo.id)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Photo>): PhotosRemoteKey? {
        return state.lastItemOrNull()?.let {
            keysDao.remoteKeysPhotoId(it.id)
        }
    }

}
package  com.mahrous.advashowimagetask.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahrous.advashowimagetask.data.PhotosDataBase
import com.mahrous.advashowimagetask.network.PhotosApi

class ViewModelFactory(
    private val photosApi: PhotosApi, private val db: PhotosDataBase
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                photosApi, db
            ) as T
            else -> throw IllegalArgumentException("Class Not Found")
        }
    }
}
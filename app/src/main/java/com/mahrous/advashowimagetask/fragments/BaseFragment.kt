package  com.mahrous.advashowimagetask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.RoomDatabase
import androidx.viewbinding.ViewBinding
import com.mahrous.advashowimagetask.R
import com.mahrous.advashowimagetask.data.PhotosDataBase
import com.mahrous.advashowimagetask.fragments.home.ViewModelFactory
import com.mahrous.advashowimagetask.network.PhotosApi
import com.mahrous.advashowimagetask.network.RetrofitClient


/**
 *
 * this class will be the parent foe every fragment that need getting dta from repository and need a view model
 * */
abstract class BaseFragment<VM : ViewModel, VB : ViewBinding, DB : RoomDatabase, Api : PhotosApi> :
    Fragment() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM
    protected val retrofitClient = RetrofitClient()
    protected lateinit var db: DB
    protected lateinit var api: Api

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        api = getFragmentApi()
        db = getFragmentDb()
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory( api, db as PhotosDataBase)
            )[getViewModel()]
        binding = getFragmentBinding(inflater, container)
        return binding.root

    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun getFragmentDb(): DB
    abstract fun getFragmentApi(): Api

}
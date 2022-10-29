package  com.mahrous.advashowimagetask.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.room.Room
import com.mahrous.advashowimagetask.R
import com.mahrous.advashowimagetask.adapters.PhotosAdapter
import com.mahrous.advashowimagetask.adapters.PhotosClickListener
import com.mahrous.advashowimagetask.data.PhotosDataBase
import com.mahrous.advashowimagetask.databinding.FragmentHomeBinding
import com.mahrous.advashowimagetask.fragments.BaseFragment
import com.mahrous.advashowimagetask.network.PhotosApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment :
    BaseFragment<HomeViewModel, FragmentHomeBinding, PhotosDataBase, PhotosApi>(),
    PhotosClickListener {


    private val photosAdapter = PhotosAdapter(this)

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photosAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    binding.progress.visibility = View.GONE
                }
                is LoadState.Error -> {
                    Toast.makeText(requireContext(), "Check your internet connection", Toast.LENGTH_SHORT).show()
                    binding.progress.visibility = View.GONE
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pager.collectLatest {
                photosAdapter.submitData(it)


            }
        }

        binding.adapter = photosAdapter
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)


    override fun getFragmentApi() = retrofitClient.buildApi(PhotosApi::class.java)
    override fun getFragmentDb() =
        Room.databaseBuilder(requireContext(), PhotosDataBase::class.java, "photos.db").build()

    override fun onPhotoClicked(photoUrl: String) {

        findNavController().navigate(R.id.displayFragment, bundleOf("url" to photoUrl))
    }
}
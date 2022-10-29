package com.mahrous.advashowimagetask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahrous.advashowimagetask.BR
import com.mahrous.advashowimagetask.R
import com.mahrous.advashowimagetask.data.Photo
import com.mahrous.advashowimagetask.databinding.PhotosItemBinding

class PhotosAdapter(private val listener: PhotosClickListener) :
    PagingDataAdapter<Photo, RecyclerView.ViewHolder>(Photo_COMPARATOR) {



    companion object {
        private val Photo_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }
    }

    inner class PhotosHolder(val bindingItem: PhotosItemBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {

        private val binding = bindingItem
        fun bind(obj: Any?) {
            binding.setVariable(BR.photoModel, obj)
            binding.executePendingBindings()
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PhotosHolder) {
            getItem(position).let {
                holder.bind(it)
            }
            holder.bindingItem.root.setOnClickListener {
                listener.onPhotoClicked(getItem(position)?.url!!)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PhotosHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.photos_item, parent, false
            )
        )

    }
}
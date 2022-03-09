package com.phoenixdevelopers.pixabay.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phoenixdevelopers.pixabay.databinding.LayoutItemImageBinding
import com.phoenixdevelopers.pixabay.models.ImageModel
import com.phoenixdevelopers.pixabay.utils.ImageDiff

class ImageAdapter(

    private val onItemClickListener: (ImageModel) -> Unit

) : ListAdapter<ImageModel, ImageAdapter.ImageViewHolder>(ImageDiff) {


    inner class ImageViewHolder(

        private val binding: LayoutItemImageBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {

                onItemClickListener(getItem(bindingAdapterPosition))

            }

        }

        fun bind(imageModel: ImageModel) {

            binding.apply {

                Glide.with(root).load(imageModel.previewImage).into(itemImage)

            }
        }
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        holder.bind(getItem(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        return ImageViewHolder(

            LayoutItemImageBinding.inflate(

                LayoutInflater.from(parent.context), parent, false

            )
        )

    }


}
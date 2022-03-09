package com.phoenixdevelopers.pixabay.utils

import androidx.recyclerview.widget.DiffUtil
import com.phoenixdevelopers.pixabay.models.ImageModel

object ImageDiff : DiffUtil.ItemCallback<ImageModel>() {

    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem.imageId == newItem.imageId
    }

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem == newItem
    }

}
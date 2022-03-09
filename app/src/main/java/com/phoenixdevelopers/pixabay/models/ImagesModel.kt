package com.phoenixdevelopers.pixabay.models

import com.google.gson.annotations.SerializedName

data class ImagesModel(

    @SerializedName("totalHits")
    val totalHits:Int,

    @SerializedName("total")
    val totalResults:Int,

    @SerializedName("hits")
    val images:List<ImageModel>

)

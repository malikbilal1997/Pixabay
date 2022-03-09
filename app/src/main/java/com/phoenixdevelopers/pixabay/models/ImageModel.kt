package com.phoenixdevelopers.pixabay.models

import com.google.gson.annotations.SerializedName

data class ImageModel(

    @SerializedName("id")
    val imageId:Int,

    @SerializedName("previewURL")
    val previewImage:String,

    @SerializedName("webformatURL")
    val webFormatImage:String,

    @SerializedName("largeImageURL")
    val largeFormatImage:String

)

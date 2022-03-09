package com.phoenixdevelopers.pixabay.network

import com.phoenixdevelopers.pixabay.models.ImagesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/")
    suspend fun getImages(

        @Query("page") page: Int,
        @Query("key") key: String

    ): ImagesModel

}
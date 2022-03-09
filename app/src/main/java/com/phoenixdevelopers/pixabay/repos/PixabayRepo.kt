package com.phoenixdevelopers.pixabay.repos

import com.phoenixdevelopers.pixabay.models.ImageModel
import com.phoenixdevelopers.pixabay.utils.Response
import kotlinx.coroutines.flow.Flow


interface PixabayRepo {

    fun getImages(page: Int): Flow<Response<List<ImageModel>>>

}
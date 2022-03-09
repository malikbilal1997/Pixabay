package com.phoenixdevelopers.pixabay.repos

import com.phoenixdevelopers.pixabay.BuildConfig
import com.phoenixdevelopers.pixabay.network.ApiInterface
import com.phoenixdevelopers.pixabay.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class PixabayRepoImpl(

    private val apiInterface: ApiInterface

) : PixabayRepo {

    override fun getImages(page: Int) = flow {

        emit(Response.Loading)

        runCatching {

            apiInterface.getImages(page, BuildConfig.PIXABAY_KEY)

        }.onSuccess { response ->

            emit(Response.Success(response.images))

        }.onFailure {

            emit(Response.Error(it))

            Timber.d("Error -> %s", it.message)

        }

    }.flowOn(Dispatchers.IO)


}
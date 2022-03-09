package com.phoenixdevelopers.pixabay.di

import com.phoenixdevelopers.pixabay.network.ApiInterface
import com.phoenixdevelopers.pixabay.repos.PixabayRepo
import com.phoenixdevelopers.pixabay.repos.PixabayRepoImpl
import com.phoenixdevelopers.pixabay.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    // Provides Retrofit Client

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    // Provides Api Interface.

    @Singleton
    @Provides
    fun providesApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    // Provides Pixabay Repository.

    @Singleton
    @Provides
    fun providesPixabayRepo(apiInterface: ApiInterface): PixabayRepo = PixabayRepoImpl(apiInterface)

}
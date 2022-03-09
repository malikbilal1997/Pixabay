package com.phoenixdevelopers.pixabay.app

import android.app.Application
import com.phoenixdevelopers.pixabay.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Pixabay : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {

            Timber.plant(Timber.DebugTree())
        }
    }
}
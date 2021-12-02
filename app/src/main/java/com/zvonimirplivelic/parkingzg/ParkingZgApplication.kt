package com.zvonimirplivelic.parkingzg

import android.app.Application
import timber.log.Timber

class ParkingZgApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
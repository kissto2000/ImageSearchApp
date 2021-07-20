package com.portfolio.imagesearchapp

import android.app.Application
import com.portfolio.imagesearchapp.di.allModule
import org.koin.android.ext.android.startKoin

class GlobalApp: Application() {

    companion object {
        var width = 0
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(applicationContext, allModule)
    }
}
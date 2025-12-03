package com.plcoding.rosafiesta

import android.app.Application
import com.plcoding.rosafiesta.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class RosaFiestaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@RosaFiestaApplication)
            androidLogger()
        }
    }
}
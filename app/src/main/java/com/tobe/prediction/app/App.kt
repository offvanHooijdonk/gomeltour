package com.tobe.prediction.app

import android.app.Application
import com.tobe.prediction.di.daoModule
import com.tobe.prediction.model.serviceModule
import com.tobe.prediction.presentation.navModule
import com.tobe.prediction.presentation.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Yahor_Fralou on 9/19/2018 3:13 PM.
 */

class App : Application() {
    companion object {
        const val LOGCAT = "PREDAPP"
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(serviceModule, daoModule, uiModule, navModule)
        }
    }
}
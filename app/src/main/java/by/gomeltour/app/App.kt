package by.gomeltour.app

import android.app.Application
import by.gomeltour.dao.daoModule
import by.gomeltour.helper.helperModule
import by.gomeltour.service.serviceModule
import by.gomeltour.presentation.navModule
import by.gomeltour.presentation.uiModule
import by.gomeltour.presentation.viewModelModule
import by.gomeltour.repository.repoModule
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
            modules(allModules)
        }
    }
}

internal val allModules = listOf(serviceModule, repoModule, daoModule, viewModelModule, uiModule, navModule, helperModule)

const val LOGCAT = "g-tour-app"
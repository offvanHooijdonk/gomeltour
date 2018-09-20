package com.tobe.prediction.app

import android.annotation.SuppressLint
import android.app.Application
import com.tobe.prediction.dao.AppDatabase
import com.tobe.prediction.di.DependencyManager

/**
 * Created by Yahor_Fralou on 9/19/2018 3:13 PM.
 */

class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic lateinit var di: DependencyManager
    }

    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(applicationContext)
        di = DependencyManager(applicationContext)
    }

}
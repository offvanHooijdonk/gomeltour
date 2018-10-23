package com.tobe.prediction.app

import android.annotation.SuppressLint
import android.app.Application
import com.tobe.prediction.dao.AppDatabase
import com.tobe.prediction.dao.buildDatabase
import com.tobe.prediction.di.DependencyManager

/**
 * Created by Yahor_Fralou on 9/19/2018 3:13 PM.
 */

class App : Application() {
    companion object {
        const val TAG = "PRED-APP"
        @SuppressLint("StaticFieldLeak")
        @JvmStatic lateinit var di: DependencyManager
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = buildDatabase(applicationContext)
        di = DependencyManager(applicationContext)
    }

}
package com.tobe.prediction.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Yahor_Fralou on 9/19/2018 4:21 PM.
 */

@Module
class AppModule(private val appContext: Context) {
    @Singleton
    @Provides
    fun provideContext() = appContext
}
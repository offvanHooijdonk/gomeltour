package com.tobe.prediction.di.app

import com.tobe.prediction.dao.AppDatabase
import com.tobe.prediction.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Yahor_Fralou on 9/20/2018 12:11 PM.
 */

@Module
class DaoModule {
    @Singleton
    @Provides
    fun provideAppDB(): AppDatabase = AppDatabase.db

    @Singleton
    @Provides
    fun provideUserDao(appDB: AppDatabase): UserDao = appDB.userDao()
}
package com.tobe.prediction.di.app

import com.tobe.prediction.app.App
import com.tobe.prediction.dao.AppDatabase
import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.IVoteDao
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
    fun provideDB(): AppDatabase = App.database

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): IUserDao = db.userDao()

    @Singleton
    @Provides
    fun providePredictDao(db: AppDatabase): IPredictDao = db.predictDao()

    @Singleton
    @Provides
    fun provideVoteDao(db: AppDatabase): IVoteDao = db.voteDao()
}
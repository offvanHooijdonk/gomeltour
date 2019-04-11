package com.tobe.prediction.di

import androidx.room.Room
import com.tobe.prediction.dao.AppDatabase
import com.tobe.prediction.dao.DB_NAME
import com.tobe.prediction.dao.IUserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val daoModule = module {
    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, DB_NAME).build() }

    single { get<AppDatabase>().userDao() }

    single { get<AppDatabase>().predictDao() }

    single { get<AppDatabase>().voteDao() }
}
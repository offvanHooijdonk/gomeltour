package com.tobe.prediction.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.tobe.prediction.domain.UserBean

@Database(entities = [UserBean::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "predict-db-0.0.1"
        lateinit var db: AppDatabase

        fun init(ctx: Context) {
            db = Room.databaseBuilder(ctx, AppDatabase::class.java, DB_NAME).build()
        }
    }

    abstract fun userDao(): UserDao
}
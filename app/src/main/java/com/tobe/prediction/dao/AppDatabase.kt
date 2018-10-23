package com.tobe.prediction.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tobe.prediction.domain.UserBean

/**
 * Created by Yahor_Fralou on 10/23/2018 4:21 PM.
 */

@Database(entities = [UserBean::class/*, Predict::class, Vote::class*/], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : IUserDao
}

const val DB_NAME = "predict-0.1"
fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
package com.tobe.prediction.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tobe.prediction.dao.converter.DateTypeConverter
import com.tobe.prediction.domain.Predict
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.domain.Vote

/**
 * Created by Yahor_Fralou on 10/23/2018 4:21 PM.
 */

@Database(entities = [UserBean::class, Predict::class, Vote::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : IUserDao
    abstract fun predictDao() : IPredictDao
    abstract fun voteDao() : IVoteDao
}

const val DB_NAME = "predict-0.2"
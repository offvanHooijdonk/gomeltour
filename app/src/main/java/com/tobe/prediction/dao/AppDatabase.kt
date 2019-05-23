package com.tobe.prediction.dao

import androidx.room.Database
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

    abstract fun userDao() : UserDao
    abstract fun predictDao() : PredictDao
    abstract fun voteDao() : VoteDao
}

const val DB_NAME = "predict-0.2.1"
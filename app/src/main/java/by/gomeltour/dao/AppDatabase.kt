package by.gomeltour.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.gomeltour.dao.converter.DateTypeConverter
import by.gomeltour.model.*

/**
 * Created by Yahor_Fralou on 10/23/2018 4:21 PM.
 */

@Database(entities = [
    UserModel::class, EventModel::class, LocationModel::class, AchievementModel::class, CheckInModel::class, AchievementLocationsModel::class
], version = 8, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun locationDao(): LocationDao
    abstract fun achievementsDao(): AchievementsDao
    abstract fun checkInDao(): CheckInDao
}

const val DB_NAME = "gomeltour-0.0.8"
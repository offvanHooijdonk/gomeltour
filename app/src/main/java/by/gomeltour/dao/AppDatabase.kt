package by.gomeltour.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.gomeltour.dao.converter.DateTypeConverter
import by.gomeltour.model.EventModel
import by.gomeltour.model.UserModel

/**
 * Created by Yahor_Fralou on 10/23/2018 4:21 PM.
 */

@Database(entities = [UserModel::class, EventModel::class], version = 3, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
}

const val DB_NAME = "gomeltour-0.0.3"
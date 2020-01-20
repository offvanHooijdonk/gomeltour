package by.gomeltour.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.gomeltour.model.AchievementModel

@Dao
interface AchievementsDao {
    @Insert
    fun insert(model: AchievementModel): Long

    @Update
    fun update(model: AchievementModel)

    @Query("select * from achievements")
    fun listAll(): List<AchievementModel>
}
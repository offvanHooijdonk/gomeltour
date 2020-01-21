package by.gomeltour.dao

import androidx.room.*
import by.gomeltour.model.AchievementModel

@Dao
interface AchievementsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(model: AchievementModel): Long

    @Update
    fun update(model: AchievementModel)

    @Query("select * from achievements order by isEarned desc, title")
    fun listAll(): List<AchievementModel>
}
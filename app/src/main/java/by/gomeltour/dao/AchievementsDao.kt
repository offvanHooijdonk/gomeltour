package by.gomeltour.dao

import androidx.paging.DataSource
import androidx.room.*
import by.gomeltour.model.AchievementLocationsModel
import by.gomeltour.model.AchievementModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(model: AchievementModel): Long

    @Update
    fun update(model: AchievementModel)

    @Query("select * from achievements where id = :id")
    fun getById(id: String): AchievementModel

    @Query("select * from achievements order by title")
    fun listAll(): Flow<List<AchievementModel>>

    @Query("select * from achievements order by title")
    fun listAllPaged(): DataSource.Factory<Int, AchievementModel>//Flow<List<AchievementModel>>

    @Insert
    fun insertLocation(model: AchievementLocationsModel)

    @Query("""select * from achievements a where not exists 
            (select locationId from achievement_locations where achievementId = a.id and locationId not in (select locationId from checks_in where userId = :userId))""")
    fun listAchieved(userId: String): List<AchievementModel>

    @Query("delete from achievement_locations where achievementId = :achievementId")
    fun deleteLocations(achievementId: String)
}
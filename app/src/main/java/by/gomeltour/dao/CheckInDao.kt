package by.gomeltour.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.gomeltour.model.CheckInModel

@Dao
interface CheckInDao {
    @Insert
    fun insert(model: CheckInModel)

    @Query("select * from checks_in where locationId = :locationId and userId = :userId order by checkTimestamp")
    fun listByUser(locationId: String, userId: String): List<CheckInModel>

    @Query("delete from checks_in where userId = :userId")
    fun removeByUser(userId: String)
}
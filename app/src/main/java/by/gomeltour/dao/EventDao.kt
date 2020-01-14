package by.gomeltour.dao

import androidx.room.*
import by.gomeltour.model.EventModel

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: EventModel): Long

    @Update
    fun update(event: EventModel)

    @Query("select * from Events where dateStart >= :startDate and dateEnd <= :endDate") // todo
    fun getEventsForPeriod(startDate: Long, endDate: Long): List<EventModel>
}
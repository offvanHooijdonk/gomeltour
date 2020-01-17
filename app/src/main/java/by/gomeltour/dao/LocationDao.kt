package by.gomeltour.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.gomeltour.model.LocationModel

@Dao
interface LocationDao {
    @Insert
    fun insert(location: LocationModel): Long

    @Update
    fun update(location: LocationModel)

    @Query("select * from locations")
    fun listAll(): List<LocationModel>
}
package by.gomeltour.dao

import androidx.room.*
import by.gomeltour.model.LocationModel

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(location: LocationModel): Long

    @Update
    fun update(location: LocationModel)

    @Query("select * from locations")
    fun listAll(): List<LocationModel>

    @Query("select * from locations where id = :id")
    fun getById(id: String): LocationModel?
}
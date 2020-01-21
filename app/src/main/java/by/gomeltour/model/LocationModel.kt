package by.gomeltour.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationModel(
        @PrimaryKey
        @ColumnInfo(index = true)
        val id: String,
        val title: String,
        val titleImageUrl: String,
        val latitude: Double,
        val longitude: Double
) {
    var distance: Float? = null
}
package by.gomeltour.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievement_locations")
data class AchievementLocationsModel(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true)
        val id: Long,
        @ColumnInfo(index = true)
        val achievementId: String,
        @ColumnInfo(index = true)
        val locationId: String
)
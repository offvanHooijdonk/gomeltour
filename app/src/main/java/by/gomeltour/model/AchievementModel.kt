package by.gomeltour.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementModel(
        @PrimaryKey
        @ColumnInfo(index = true)
        val id: String,
        val title: String,
        val description: String,
        val imageUrl: String
) {
        @Ignore
        var locations: List<String> = emptyList()
        @Ignore
        var isEarned: Boolean = false
}
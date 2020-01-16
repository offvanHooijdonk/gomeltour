package by.gomeltour.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Events")
data class EventModel(
        @PrimaryKey
        @ColumnInfo(index = true)
        val id: String,
        val title: String,
        val description: String?,
        val dateStart: Date,
        val dateEnd: Date?,
        val timeStart: Date?,
        val timeEnd: Date?,
        val titleImageUrl: String?
)
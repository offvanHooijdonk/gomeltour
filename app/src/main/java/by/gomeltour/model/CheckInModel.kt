package by.gomeltour.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "checks_in")
data class CheckInModel(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true)
        val id: Long,
        val locationId: String,
        val userId: String,
        val checkTimestamp: Date
)
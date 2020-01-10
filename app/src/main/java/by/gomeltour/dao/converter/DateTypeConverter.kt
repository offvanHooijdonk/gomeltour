package by.gomeltour.dao.converter

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun convertDateToLong(date: Date) = date.time

    @TypeConverter
    fun convertLongToDate(time: Long) = Date(time)

    @TypeConverter
    fun convertListToString(list: Array<String>) = list.joinToString(SEPARATOR_OPTIONS)

    @TypeConverter
    fun convertStringToList(str: String) = str.split(SEPARATOR_OPTIONS).toTypedArray()
}

private const val SEPARATOR_OPTIONS = "|"
package com.tobe.prediction.dao.converter

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun convertDateToLong(date: Date) = date.time

    @TypeConverter
    fun convertLongToDate(time: Long) = Date(time)

    @TypeConverter
    fun convertListToString(list: List<String>) = list[0]

    @TypeConverter
    fun convertStringToList(str: String) = listOf(str)
}
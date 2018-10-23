package com.tobe.prediction.dao.converter

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun convertDateToLong(date: Date) = date.time

    @TypeConverter
    fun convertLongToDate(time: Long) = Date(time)
}
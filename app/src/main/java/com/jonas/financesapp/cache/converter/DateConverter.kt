package com.jonas.financesapp.cache.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    @JvmStatic
    fun toDate(date: Long): Date = Date(date)

}
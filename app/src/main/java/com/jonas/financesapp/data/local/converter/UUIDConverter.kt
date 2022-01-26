package com.jonas.financesapp.data.local.converter

import androidx.room.TypeConverter
import java.util.*

object UUIDConverter {

    @TypeConverter
    @JvmStatic
    fun fromUUID(uuid: UUID): String = uuid.toString()

    @TypeConverter
    @JvmStatic
    fun toUUID(uuid: String): UUID = UUID.fromString(uuid)

}
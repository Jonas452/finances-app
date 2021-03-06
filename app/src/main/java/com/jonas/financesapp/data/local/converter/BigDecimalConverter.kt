package com.jonas.financesapp.data.local.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

object BigDecimalConverter {

    @TypeConverter
    @JvmStatic
    fun fromBigDecimal(bigDecimal: BigDecimal): Double = bigDecimal.toDouble()

    @TypeConverter
    @JvmStatic
    fun toBigDecimal(double: Double): BigDecimal = BigDecimal(double)

}
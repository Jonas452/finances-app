package com.jonas.financesapp.cache.converter

import androidx.room.TypeConverter
import com.jonas.financesapp.model.IncomeExpenseType

object IncomeExpenseTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(incomeExpenseType: String): IncomeExpenseType =
        IncomeExpenseType.valueOf(incomeExpenseType)

    @TypeConverter
    @JvmStatic
    fun toString(incomeExpenseType: IncomeExpenseType): String = incomeExpenseType.toString()

}
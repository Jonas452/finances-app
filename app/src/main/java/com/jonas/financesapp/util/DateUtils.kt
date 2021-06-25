package com.jonas.financesapp.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val DEFAULT_FORMAT_DATE_WITHOUT_TIME = "yyyy-MM-dd"
    const val DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME = "dd/MM/yyyy"

    @SuppressLint("SimpleDateFormat")
    @Throws(ParseException::class)
    fun formatDate(dateStr: String?, formatStr: String?): Date {
        return SimpleDateFormat(formatStr ?: DEFAULT_FORMAT_DATE_WITHOUT_TIME).parse(
            dateStr!!
        )!!
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date?, formatStr: String?): String {
        return SimpleDateFormat(formatStr ?: DEFAULT_FORMAT_DATE_WITHOUT_TIME).format(
            date!!
        )
    }

}
package com.jonas.financesapp.model

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.jonas.financesapp.R
import com.jonas.financesapp.util.DateUtils
import com.jonas.financesapp.util.formatToMoney
import java.math.BigDecimal
import java.util.*

data class IncomeExpenseItem(
    var id: UUID = UUID.randomUUID(),
    var amount: BigDecimal,
    var description: String,
    var date: Date,
    var type: IncomeExpenseType,
) {

    @DrawableRes
    fun getTypeIcon(): Int {
        return when(type) {
            IncomeExpenseType.INCOME -> R.drawable.ic_arrow_upward
            IncomeExpenseType.EXPENSE -> R.drawable.ic_arrow_downward
        }
    }

    @ColorInt
    fun getTypeColor(): Int {
        return when(type) {
            IncomeExpenseType.INCOME -> R.color.green
            IncomeExpenseType.EXPENSE -> R.color.red
        }
    }

    fun getDateReadable(): String {
        return DateUtils.formatDate(
            date,
            DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME
        )
    }

    fun getAmountAsMoney(context: Context) = amount.toDouble().formatToMoney(context)

}

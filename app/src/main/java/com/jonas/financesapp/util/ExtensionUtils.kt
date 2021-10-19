package com.jonas.financesapp.util

import android.content.Context
import com.jonas.financesapp.R
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

fun Double.formatToMoney(context: Context): String {
    return context.getString(R.string.money_sign, this.roundTo(2).toString())
}
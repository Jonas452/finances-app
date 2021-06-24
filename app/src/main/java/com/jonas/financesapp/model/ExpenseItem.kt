package com.jonas.financesapp.model

import java.math.BigDecimal
import java.util.*

data class ExpenseItem(
    val id: Long?,
    val amount: BigDecimal,
    val description: String,
    val date: Date,
    val payed: Boolean,
)

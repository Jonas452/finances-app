package com.jonas.financesapp.model

import java.math.BigDecimal
import java.util.*

data class IncomeExpenseItem(
    var id: UUID = UUID.randomUUID(),
    var amount: BigDecimal,
    var description: String,
    var date: Date,
    var type: IncomeExpenseType,
)

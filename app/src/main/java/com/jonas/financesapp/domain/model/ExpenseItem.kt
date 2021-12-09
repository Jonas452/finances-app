package com.jonas.financesapp.domain.model

import java.math.BigDecimal
import java.util.*

data class ExpenseItem(
    var id: UUID = UUID.randomUUID(),
    var amount: BigDecimal,
    var description: String,
    var date: Date,
    var paid: Boolean,
)

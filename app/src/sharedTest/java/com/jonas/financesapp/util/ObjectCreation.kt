package com.jonas.financesapp.util

import com.jonas.financesapp.cache.entity.ExpenseEntity
import com.jonas.financesapp.cache.entity.IncomeEntity
import com.jonas.financesapp.model.ExpenseItem
import com.jonas.financesapp.model.IncomeItem
import java.math.BigDecimal
import java.util.*

fun createExpenseEntity(
    id: UUID = UUID.randomUUID(),
    amount: BigDecimal = BigDecimal(2),
    description: String = "description",
    date: Date = Date(),
    paid: Boolean = true,
) = ExpenseEntity(
    id,
    amount,
    description,
    date,
    paid,
)

fun createExpenseItem(
    id: UUID = UUID.randomUUID(),
    amount: BigDecimal = BigDecimal(2),
    description: String = "description",
    date: Date = Date(),
    paid: Boolean = true,
) = ExpenseItem(
    id,
    amount,
    description,
    date,
    paid,
)

fun createIncomeEntity(
    id: UUID = UUID.randomUUID(),
    amount: BigDecimal = BigDecimal(2),
    description: String = "description",
    date: Date = Date(),
    received: Boolean = true,
) = IncomeEntity(
    id,
    amount,
    description,
    date,
    received,
)

fun createIncomeItem(
    id: UUID = UUID.randomUUID(),
    amount: BigDecimal = BigDecimal(2),
    description: String = "description",
    date: Date = Date(),
    received: Boolean = true,
) = IncomeItem(
    id,
    amount,
    description,
    date,
    received,
)
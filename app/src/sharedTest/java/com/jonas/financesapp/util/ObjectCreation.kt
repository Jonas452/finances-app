package com.jonas.financesapp.util

import com.jonas.financesapp.cache.entity.ExpenseEntity
import com.jonas.financesapp.cache.entity.IncomeEntity
import com.jonas.financesapp.model.ExpenseItem
import com.jonas.financesapp.model.IncomeItem
import java.math.BigDecimal
import java.util.*

fun createExpenseEntity(
    id: Long? = null,
    amount: BigDecimal = BigDecimal(2),
    description: String = "description",
    date: Date = Date(),
    payed: Boolean = true,
) = ExpenseEntity(
    id,
    amount,
    description,
    date,
    payed,
)

fun createExpenseItem(
    id: Long? = null,
    amount: BigDecimal = BigDecimal(2),
    description: String = "description",
    date: Date = Date(),
    payed: Boolean = true,
) = ExpenseItem(
    id,
    amount,
    description,
    date,
    payed,
)

fun createIncomeEntity(
    id: Long? = null,
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
    id: Long? = null,
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
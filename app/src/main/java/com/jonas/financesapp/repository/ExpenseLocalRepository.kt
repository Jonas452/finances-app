package com.jonas.financesapp.repository

import com.jonas.financesapp.model.ExpenseItem
import java.util.*

interface ExpenseLocalRepository {

    suspend fun insertExpense(expenseItem: ExpenseItem)
    suspend fun updateExpense(expenseItem: ExpenseItem): Int
    suspend fun getExpenseById(id: UUID): ExpenseItem?

}
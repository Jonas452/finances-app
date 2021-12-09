package com.jonas.financesapp.repository

import com.jonas.financesapp.domain.model.ExpenseItem
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ExpenseLocalRepository {

    suspend fun insertExpense(expenseItem: ExpenseItem)
    suspend fun updateExpense(expenseItem: ExpenseItem): Int
    suspend fun getExpenseById(id: UUID): ExpenseItem?
    fun getSumAllExpense(): Flow<Double>

}
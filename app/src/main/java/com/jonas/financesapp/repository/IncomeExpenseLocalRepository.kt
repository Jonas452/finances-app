package com.jonas.financesapp.repository

import com.jonas.financesapp.model.IncomeExpenseItem
import kotlinx.coroutines.flow.Flow

interface IncomeExpenseLocalRepository {

    fun getAllIncomeExpense(): Flow<List<IncomeExpenseItem>>

}
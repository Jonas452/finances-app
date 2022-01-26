package com.jonas.financesapp.data.repository

import com.jonas.financesapp.domain.model.IncomeExpenseItem
import kotlinx.coroutines.flow.Flow

interface IncomeExpenseLocalRepository {

    fun getAllIncomeExpense(): Flow<List<IncomeExpenseItem>>

}
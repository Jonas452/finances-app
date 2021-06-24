package com.jonas.financesapp.repository

import com.jonas.financesapp.model.IncomeItem
import java.util.*

interface IncomeLocalRepository {

    suspend fun insertIncome(incomeItem: IncomeItem)
    suspend fun updateIncome(incomeItem: IncomeItem): Int
    suspend fun getIncomeById(id: UUID): IncomeItem?

}
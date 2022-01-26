package com.jonas.financesapp.data.repository

import com.jonas.financesapp.domain.model.IncomeItem
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IncomeLocalRepository {

    suspend fun insertIncome(incomeItem: IncomeItem)
    suspend fun updateIncome(incomeItem: IncomeItem): Int
    suspend fun getIncomeById(id: UUID): IncomeItem?
    fun getSumAllIncome(): Flow<Double>

}
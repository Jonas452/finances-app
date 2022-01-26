package com.jonas.financesapp.data.repository

import com.jonas.financesapp.data.local.dao.IncomeExpenseViewDao
import com.jonas.financesapp.data.local.mapper.IncomeExpenseMapper
import com.jonas.financesapp.domain.model.IncomeExpenseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IncomeExpenseLocalRepositoryImpl @Inject constructor(
    private val incomeExpenseViewDao: IncomeExpenseViewDao,
    private val incomeExpenseMapper: IncomeExpenseMapper,
) : IncomeExpenseLocalRepository {

    override fun getAllIncomeExpense(): Flow<List<IncomeExpenseItem>> {
        return incomeExpenseViewDao.getAllIncomeExpense().map {
            incomeExpenseMapper.toModelList(it)
        }
    }

}
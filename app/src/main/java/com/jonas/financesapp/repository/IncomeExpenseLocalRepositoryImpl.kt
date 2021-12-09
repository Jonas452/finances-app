package com.jonas.financesapp.repository

import com.jonas.financesapp.cache.dao.IncomeExpenseViewDao
import com.jonas.financesapp.cache.mapper.IncomeExpenseMapper
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
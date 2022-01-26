package com.jonas.financesapp.data.repository

import com.jonas.financesapp.data.local.dao.ExpenseDao
import com.jonas.financesapp.data.local.mapper.ExpenseMapper
import com.jonas.financesapp.domain.model.ExpenseItem
import com.jonas.financesapp.util.Constants.DEFAULT_MONEY_VALUE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class ExpenseLocalRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val expenseMapper: ExpenseMapper,
) : ExpenseLocalRepository {

    override suspend fun insertExpense(expenseItem: ExpenseItem) {
        expenseDao.insertExpense(
            expenseMapper.toEntity(
                expenseItem
            )
        )
    }

    override suspend fun updateExpense(expenseItem: ExpenseItem): Int {
        return expenseDao.updateExpense(
            expenseMapper.toEntity(
                expenseItem
            )
        )
    }

    override suspend fun getExpenseById(id: UUID): ExpenseItem? {
        val entity = expenseDao.getExpenseById(id) ?: return null

        return expenseMapper.toModel(entity)
    }

    override fun getSumAllExpense(): Flow<Double> {
        return expenseDao.getSumAllExpense().map { it ?: DEFAULT_MONEY_VALUE }
    }

}
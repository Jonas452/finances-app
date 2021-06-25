package com.jonas.financesapp.repository

import com.jonas.financesapp.cache.dao.ExpenseDao
import com.jonas.financesapp.cache.mapper.ExpenseMapper
import com.jonas.financesapp.model.ExpenseItem
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

}
package com.jonas.financesapp.repository

import com.jonas.financesapp.cache.dao.IncomeDao
import com.jonas.financesapp.cache.mapper.IncomeMapper
import com.jonas.financesapp.model.IncomeItem
import com.jonas.financesapp.util.Constants.DEFAULT_MONEY_VALUE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class IncomeLocalRepositoryImpl @Inject constructor(
    private val incomeDao: IncomeDao,
    private val incomeMapper: IncomeMapper,
) : IncomeLocalRepository {

    override suspend fun insertIncome(incomeItem: IncomeItem) {
        incomeDao.insertIncome(
            incomeMapper.toEntity(
                incomeItem
            )
        )
    }

    override suspend fun updateIncome(incomeItem: IncomeItem): Int {
        return incomeDao.updateIncome(
            incomeMapper.toEntity(
                incomeItem
            )
        )
    }

    override suspend fun getIncomeById(id: UUID): IncomeItem? {
        val entity = incomeDao.getIncomeById(id) ?: return null

        return incomeMapper.toModel(entity)
    }

    override fun getSumAllIncome(): Flow<Double> {
        return incomeDao.getSumAllIncome().map { it ?: DEFAULT_MONEY_VALUE }
    }

}
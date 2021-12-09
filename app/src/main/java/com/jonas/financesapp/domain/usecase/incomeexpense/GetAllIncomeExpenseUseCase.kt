package com.jonas.financesapp.domain.usecase.incomeexpense

import com.jonas.financesapp.domain.model.IncomeExpenseItem
import com.jonas.financesapp.repository.IncomeExpenseLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllIncomeExpenseUseCase @Inject constructor(
    private val incomeExpenseLocalRepository: IncomeExpenseLocalRepository,
) {

    operator fun invoke(): Flow<List<IncomeExpenseItem>> =
        incomeExpenseLocalRepository.getAllIncomeExpense()

}
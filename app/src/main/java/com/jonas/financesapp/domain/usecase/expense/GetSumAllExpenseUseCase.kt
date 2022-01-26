package com.jonas.financesapp.domain.usecase.expense

import com.jonas.financesapp.data.repository.ExpenseLocalRepository
import javax.inject.Inject

class GetSumAllExpenseUseCase @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
) {

    operator fun invoke() = expenseLocalRepository.getSumAllExpense()

}
package com.jonas.financesapp.usecase.expense

import com.jonas.financesapp.repository.ExpenseLocalRepository
import javax.inject.Inject

class GetSumAllExpenseUseCase @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
) {

    operator fun invoke() = expenseLocalRepository.getSumAllExpense()

}
package com.jonas.financesapp.domain.usecase.expense

import com.jonas.financesapp.domain.model.ExpenseItem
import com.jonas.financesapp.repository.ExpenseLocalRepository
import javax.inject.Inject

class InsertExpenseUseCase @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
) {

    suspend operator fun invoke(expenseItem: ExpenseItem) =
        expenseLocalRepository.insertExpense(expenseItem)

}
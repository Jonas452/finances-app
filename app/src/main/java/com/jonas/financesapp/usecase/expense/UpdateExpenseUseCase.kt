package com.jonas.financesapp.usecase.expense

import com.jonas.financesapp.model.ExpenseItem
import com.jonas.financesapp.repository.ExpenseLocalRepository
import javax.inject.Inject

class UpdateExpenseUseCase @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
) {

    suspend operator fun invoke(expenseItem: ExpenseItem) =
        expenseLocalRepository.updateExpense(expenseItem)

}
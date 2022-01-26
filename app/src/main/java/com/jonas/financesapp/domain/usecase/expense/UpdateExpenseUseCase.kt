package com.jonas.financesapp.domain.usecase.expense

import com.jonas.financesapp.data.repository.ExpenseLocalRepository
import com.jonas.financesapp.domain.model.ExpenseItem
import javax.inject.Inject

class UpdateExpenseUseCase @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
) {

    suspend operator fun invoke(expenseItem: ExpenseItem) =
        expenseLocalRepository.updateExpense(expenseItem)

}
package com.jonas.financesapp.usecase.expense

import com.jonas.financesapp.model.ExpenseItem
import com.jonas.financesapp.repository.ExpenseLocalRepository
import java.util.*
import javax.inject.Inject

class GetExpenseById @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
) {

    suspend operator fun invoke(id: UUID): ExpenseItem? = expenseLocalRepository.getExpenseById(id)

}
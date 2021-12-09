package com.jonas.financesapp.domain.usecase.expense

import com.jonas.financesapp.domain.model.ExpenseItem
import com.jonas.financesapp.repository.ExpenseLocalRepository
import java.util.*
import javax.inject.Inject

class GetExpenseByIdUseCase @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
) {

    suspend operator fun invoke(id: UUID): ExpenseItem? = expenseLocalRepository.getExpenseById(id)

}
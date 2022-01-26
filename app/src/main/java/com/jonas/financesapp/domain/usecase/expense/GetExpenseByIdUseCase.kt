package com.jonas.financesapp.domain.usecase.expense

import com.jonas.financesapp.data.repository.ExpenseLocalRepository
import com.jonas.financesapp.domain.model.ExpenseItem
import java.util.*
import javax.inject.Inject

class GetExpenseByIdUseCase @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
) {

    suspend operator fun invoke(id: UUID): ExpenseItem? = expenseLocalRepository.getExpenseById(id)

}
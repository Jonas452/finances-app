package com.jonas.financesapp.usecase.income

import com.jonas.financesapp.model.IncomeItem
import com.jonas.financesapp.repository.IncomeLocalRepository
import java.util.*
import javax.inject.Inject

class GetIncomeByIdUseCase @Inject constructor(
    private val incomeLocalRepository: IncomeLocalRepository,
) {

    suspend operator fun invoke(id: UUID): IncomeItem? = incomeLocalRepository.getIncomeById(id)

}
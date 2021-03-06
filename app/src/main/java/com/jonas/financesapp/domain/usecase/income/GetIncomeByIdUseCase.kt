package com.jonas.financesapp.domain.usecase.income

import com.jonas.financesapp.data.repository.IncomeLocalRepository
import com.jonas.financesapp.domain.model.IncomeItem
import java.util.*
import javax.inject.Inject

class GetIncomeByIdUseCase @Inject constructor(
    private val incomeLocalRepository: IncomeLocalRepository,
) {

    suspend operator fun invoke(id: UUID): IncomeItem? = incomeLocalRepository.getIncomeById(id)

}
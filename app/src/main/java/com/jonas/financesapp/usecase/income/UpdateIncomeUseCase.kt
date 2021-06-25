package com.jonas.financesapp.usecase.income

import com.jonas.financesapp.model.IncomeItem
import com.jonas.financesapp.repository.IncomeLocalRepository
import javax.inject.Inject

class UpdateIncomeUseCase @Inject constructor(
    private val incomeLocalRepository: IncomeLocalRepository,
) {

    suspend operator fun invoke(incomeItem: IncomeItem) =
        incomeLocalRepository.updateIncome(incomeItem)

}
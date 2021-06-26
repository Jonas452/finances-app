package com.jonas.financesapp.usecase.income

import com.jonas.financesapp.repository.IncomeLocalRepository
import javax.inject.Inject

class GetSumAllIncomeUseCase @Inject constructor(
    private val incomeLocalRepository: IncomeLocalRepository,
) {

    operator fun invoke() = incomeLocalRepository.getSumAllIncome()

}
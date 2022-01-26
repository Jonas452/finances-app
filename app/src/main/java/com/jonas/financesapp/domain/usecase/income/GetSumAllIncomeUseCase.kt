package com.jonas.financesapp.domain.usecase.income

import com.jonas.financesapp.data.repository.IncomeLocalRepository
import javax.inject.Inject

class GetSumAllIncomeUseCase @Inject constructor(
    private val incomeLocalRepository: IncomeLocalRepository,
) {

    operator fun invoke() = incomeLocalRepository.getSumAllIncome()

}
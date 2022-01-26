package com.jonas.financesapp.domain.usecase.income

import com.jonas.financesapp.data.repository.IncomeLocalRepository
import com.jonas.financesapp.domain.model.IncomeItem
import javax.inject.Inject

class InsertIncomeUseCase @Inject constructor(
    private val incomeLocalRepository: IncomeLocalRepository,
) {

    suspend operator fun invoke(incomeItem: IncomeItem) =
        incomeLocalRepository.insertIncome(incomeItem)

}
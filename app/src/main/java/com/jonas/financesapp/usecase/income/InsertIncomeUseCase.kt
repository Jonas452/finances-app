package com.jonas.financesapp.usecase.income

import com.jonas.financesapp.model.IncomeItem
import com.jonas.financesapp.repository.IncomeLocalRepository
import javax.inject.Inject

class InsertIncomeUseCase @Inject constructor(
    private val incomeLocalRepository: IncomeLocalRepository,
) {

    suspend operator fun invoke(incomeItem: IncomeItem) =
        incomeLocalRepository.insertIncome(incomeItem)

}
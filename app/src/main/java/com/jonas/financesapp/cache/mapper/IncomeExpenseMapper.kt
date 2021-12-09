package com.jonas.financesapp.cache.mapper

import com.jonas.financesapp.cache.entity.IncomeExpenseView
import com.jonas.financesapp.domain.model.IncomeExpenseItem
import java.math.BigDecimal
import javax.inject.Inject

class IncomeExpenseMapper @Inject constructor() {

    private fun toModel(view: IncomeExpenseView): IncomeExpenseItem {
        return IncomeExpenseItem(
            view.id,
            view.amount.setScale(2, BigDecimal.ROUND_HALF_EVEN),
            view.description,
            view.date,
            view.type,
        )
    }

    fun toModelList(incomeExpenseViewList: List<IncomeExpenseView>): List<IncomeExpenseItem> {
        return incomeExpenseViewList.map {
            toModel(it)
        }
    }

}
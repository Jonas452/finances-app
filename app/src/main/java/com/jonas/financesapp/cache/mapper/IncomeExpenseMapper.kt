package com.jonas.financesapp.cache.mapper

import com.jonas.financesapp.cache.entity.IncomeExpenseView
import com.jonas.financesapp.model.IncomeExpenseItem
import javax.inject.Inject

class IncomeExpenseMapper @Inject constructor() {

    fun toModel(incomeExpenseView: IncomeExpenseView): IncomeExpenseItem {
        return IncomeExpenseItem(
            incomeExpenseView.id,
            incomeExpenseView.amount,
            incomeExpenseView.description,
            incomeExpenseView.date,
            incomeExpenseView.type,
        )
    }

    fun toModelList(incomeExpenseViewList: List<IncomeExpenseView>): List<IncomeExpenseItem> {
        return incomeExpenseViewList.map {
            toModel(it)
        }
    }

}
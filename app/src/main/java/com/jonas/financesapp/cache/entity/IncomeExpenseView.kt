package com.jonas.financesapp.cache.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.jonas.financesapp.cache.entity.IncomeExpenseView.Companion.VIEW_NAME
import com.jonas.financesapp.domain.model.IncomeExpenseType
import java.math.BigDecimal
import java.util.*


@DatabaseView(
    value = """
        SELECT
            inc.id,
            inc.amount,
            inc.description,
            inc.date,
            'INCOME' AS type
        FROM ${IncomeEntity.TABLE_NAME} as inc
        
        UNION ALL
        
        SELECT
            inc.id,
            inc.amount,
            inc.description,
            inc.date,
            'EXPENSE' AS type
        FROM ${ExpenseEntity.TABLE_NAME} as inc
    """,
    viewName = VIEW_NAME
)
data class IncomeExpenseView(
    @ColumnInfo(name = COLUMN_ID) var id: UUID,
    @ColumnInfo(name = COLUMN_AMOUNT) var amount: BigDecimal,
    @ColumnInfo(name = COLUMN_DESCRIPTION) var description: String,
    @ColumnInfo(name = COLUMN_DATE) var date: Date,
    @ColumnInfo(name = COLUMN_TYPE) var type: IncomeExpenseType,
) {

    companion object {

        const val VIEW_NAME = "income_expense_view"

        const val COLUMN_ID = "id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE = "date"
        const val COLUMN_TYPE = "type"

    }

}
package com.jonas.financesapp.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jonas.financesapp.cache.entity.ExpenseEntity.Companion.TABLE_NAME
import java.math.BigDecimal
import java.util.*

@Entity(tableName = TABLE_NAME)
data class ExpenseEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) var id: UUID,
    @ColumnInfo(name = COLUMN_AMOUNT) var amount: BigDecimal,
    @ColumnInfo(name = COLUMN_DESCRIPTION) var description: String,
    @ColumnInfo(name = COLUMN_DATE) var date: Date,
    @ColumnInfo(name = COLUMN_PAID) var paid: Boolean,
) {

    companion object {

        const val TABLE_NAME = "expense"

        const val COLUMN_ID = "id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE = "date"
        const val COLUMN_PAID = "paid"

    }

}

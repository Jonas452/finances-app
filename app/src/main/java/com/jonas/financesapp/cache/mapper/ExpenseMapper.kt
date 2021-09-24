package com.jonas.financesapp.cache.mapper

import com.jonas.financesapp.cache.entity.ExpenseEntity
import com.jonas.financesapp.model.ExpenseItem
import java.math.BigDecimal
import javax.inject.Inject

class ExpenseMapper @Inject constructor() {

    fun toModel(entity: ExpenseEntity): ExpenseItem {
        return ExpenseItem(
            entity.id,
            entity.amount.setScale(2, BigDecimal.ROUND_HALF_EVEN),
            entity.description,
            entity.date,
            entity.paid,
        )
    }

    fun toEntity(model: ExpenseItem): ExpenseEntity {
        return ExpenseEntity(
            model.id,
            model.amount.setScale(2, BigDecimal.ROUND_HALF_EVEN),
            model.description,
            model.date,
            model.paid,
        )
    }

    fun toModelList(entityList: List<ExpenseEntity>): List<ExpenseItem> {
        return entityList.map {
            toModel(it)
        }
    }

}
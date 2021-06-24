package com.jonas.financesapp.cache.mapper

import com.jonas.financesapp.cache.entity.ExpenseEntity
import com.jonas.financesapp.model.ExpenseItem

class ExpenseMapper {

    fun toModel(entity: ExpenseEntity): ExpenseItem {
        return ExpenseItem(
            entity.id,
            entity.amount,
            entity.description,
            entity.date,
            entity.payed,
        )
    }

    fun toEntity(model: ExpenseItem): ExpenseEntity {
        return ExpenseEntity(
            model.id,
            model.amount,
            model.description,
            model.date,
            model.payed,
        )
    }

    fun toModelList(entityList: List<ExpenseEntity>): List<ExpenseItem> {
        return entityList.map {
            toModel(it)
        }
    }

}
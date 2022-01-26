package com.jonas.financesapp.data.local.mapper

import com.jonas.financesapp.data.local.entity.IncomeEntity
import com.jonas.financesapp.domain.model.IncomeItem
import java.math.BigDecimal
import javax.inject.Inject

class IncomeMapper @Inject constructor() {

    fun toModel(entity: IncomeEntity): IncomeItem {
        return IncomeItem(
            entity.id,
            entity.amount.setScale(2, BigDecimal.ROUND_HALF_EVEN),
            entity.description,
            entity.date,
            entity.received,
        )
    }

    fun toEntity(model: IncomeItem): IncomeEntity {
        return IncomeEntity(
            model.id,
            model.amount.setScale(2, BigDecimal.ROUND_HALF_EVEN),
            model.description,
            model.date,
            model.received,
        )
    }

    fun toModelList(entityList: List<IncomeEntity>): List<IncomeItem> {
        return entityList.map {
            toModel(it)
        }
    }

}
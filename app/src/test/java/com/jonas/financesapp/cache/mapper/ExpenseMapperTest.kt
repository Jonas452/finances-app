package com.jonas.financesapp.cache.mapper

import com.google.common.truth.Truth.assertThat
import com.jonas.financesapp.cache.entity.ExpenseEntity
import com.jonas.financesapp.model.ExpenseItem
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class ExpenseMapperTest {

    private lateinit var expenseMapper: ExpenseMapper

    @Before
    fun setUp() {
        expenseMapper = ExpenseMapper()
    }

    @Test
    fun `map expense entity to item returns expense item`() {
        // Arrange
        val entity = ExpenseEntity(
            null,
            BigDecimal(2),
            "description",
            Date(),
            false,
        )

        // Act
        val result = expenseMapper.toModel(entity)

        // Assert
        assertThat(result.id).isEqualTo(entity.id)
        assertThat(result.amount).isEqualTo(entity.amount)
        assertThat(result.description).isEqualTo(entity.description)
        assertThat(result.date).isEqualTo(entity.date)
        assertThat(result.payed).isEqualTo(entity.payed)
    }

    @Test
    fun `map expense item to entity returns expense entity`() {
        // Arrange
        val model = ExpenseItem(
            null,
            BigDecimal(2),
            "description",
            Date(),
            false,
        )

        // Act
        val result = expenseMapper.toEntity(model)

        // Assert
        assertThat(result.id).isEqualTo(model.id)
        assertThat(result.amount).isEqualTo(model.amount)
        assertThat(result.description).isEqualTo(model.description)
        assertThat(result.date).isEqualTo(model.date)
        assertThat(result.payed).isEqualTo(model.payed)
    }

    @Test
    fun `map expense entity list to expense item list returns expense item list`() {
        // Arrange
        val entityList = listOf(
            ExpenseEntity(
                null,
                BigDecimal(2),
                "description",
                Date(),
                false,
            ),
            ExpenseEntity(
                null,
                BigDecimal(3),
                "description2",
                Date(),
                true,
            ),
        )

        // Act
        val result = expenseMapper.toModelList(entityList)

        // Assert
        assertThat(result.size).isEqualTo(entityList.size)
    }

}
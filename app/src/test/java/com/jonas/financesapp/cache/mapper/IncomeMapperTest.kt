package com.jonas.financesapp.cache.mapper

import com.google.common.truth.Truth.assertThat
import com.jonas.financesapp.cache.entity.IncomeEntity
import com.jonas.financesapp.model.IncomeItem
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class IncomeMapperTest {

    private lateinit var incomeMapper: IncomeMapper

    @Before
    fun setUp() {
        incomeMapper = IncomeMapper()
    }

    @Test
    fun `map income entity to income item returns income item`() {
        // Arrange
        val entity = IncomeEntity(
            null,
            BigDecimal(2),
            "description",
            Date(),
            true
        )

        // Act
        val result = incomeMapper.toModel(entity)

        // Assert
        assertThat(result.id).isEqualTo(entity.id)
        assertThat(result.amount).isEqualTo(entity.amount)
        assertThat(result.description).isEqualTo(entity.description)
        assertThat(result.date).isEqualTo(entity.date)
        assertThat(result.received).isEqualTo(entity.received)
    }

    @Test
    fun `map income model to income entity returns income entity`() {
        // Arrange
        val model = IncomeItem(
            null,
            BigDecimal(2),
            "description",
            Date(),
            true
        )

        // Act
        val result = incomeMapper.toEntity(model)

        // Assert
        assertThat(result.id).isEqualTo(model.id)
        assertThat(result.amount).isEqualTo(model.amount)
        assertThat(result.description).isEqualTo(model.description)
        assertThat(result.date).isEqualTo(model.date)
        assertThat(result.received).isEqualTo(model.received)
    }

    @Test
    fun `map income entity list to income item list returns income item list`() {
        // Arrange
        val entityList = listOf(
            IncomeEntity(
                null,
                BigDecimal(2),
                "description",
                Date(),
                false,
            ),
            IncomeEntity(
                null,
                BigDecimal(3),
                "description2",
                Date(),
                true,
            ),
        )

        // Act
        val result = incomeMapper.toModelList(entityList)

        // Assert
        assertThat(result.size).isEqualTo(entityList.size)
    }

}
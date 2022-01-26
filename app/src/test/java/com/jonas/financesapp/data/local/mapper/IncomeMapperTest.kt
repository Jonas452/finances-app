package com.jonas.financesapp.data.local.mapper

import com.google.common.truth.Truth.assertThat
import com.jonas.financesapp.util.createIncomeEntity
import com.jonas.financesapp.util.createIncomeItem
import org.junit.Before
import org.junit.Test

class IncomeMapperTest {

    private lateinit var incomeMapper: IncomeMapper

    @Before
    fun setUp() {
        incomeMapper = IncomeMapper()
    }

    @Test
    fun `map income entity to income item returns income item`() {
        // Arrange
        val entity = createIncomeEntity()

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
        val model = createIncomeItem()

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
            createIncomeEntity(),
            createIncomeEntity(),
        )

        // Act
        val result = incomeMapper.toModelList(entityList)

        // Assert
        assertThat(result.size).isEqualTo(entityList.size)
    }

}
package com.jonas.financesapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.jonas.financesapp.cache.FinancesDatabase
import com.jonas.financesapp.cache.mapper.IncomeMapper
import com.jonas.financesapp.util.createIncomeItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class IncomeLocalRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var incomeLocalRepository: IncomeLocalRepository
    private lateinit var database: FinancesDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FinancesDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        incomeLocalRepository = IncomeLocalRepositoryImpl(
            database.getIncomeDao(),
            IncomeMapper(),
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertIncome() = runBlockingTest {
        // Arrange
        val incomeItem = createIncomeItem()
        incomeLocalRepository.insertIncome(incomeItem)

        // Act
        val loaded = incomeLocalRepository.getIncomeById(incomeItem.id)

        // Assert
        assertThat(loaded).isNotNull()
        assertThat(loaded!!.id).isEqualTo(incomeItem.id)
        assertThat(loaded.amount).isEqualTo(incomeItem.amount)
        assertThat(loaded.description).isEqualTo(incomeItem.description)
        assertThat(loaded.date).isEqualTo(incomeItem.date)
        assertThat(loaded.received).isEqualTo(incomeItem.received)

    }

    @Test
    fun updateIncome() = runBlockingTest {
        // Arrange
        val incomeItem = createIncomeItem()
        incomeLocalRepository.insertIncome(incomeItem)
        val updatedIncome = createIncomeItem(
            incomeItem.id,
            incomeItem.amount,
            "new description",
            incomeItem.date,
            incomeItem.received,
        )

        // Act
        incomeLocalRepository.updateIncome(updatedIncome)
        val loaded = incomeLocalRepository.getIncomeById(updatedIncome.id)

        // Assert
        assertThat(loaded).isNotNull()
        assertThat(loaded!!.id).isEqualTo(updatedIncome.id)
        assertThat(loaded.amount).isEqualTo(updatedIncome.amount)
        assertThat(loaded.description).isEqualTo(updatedIncome.description)
        assertThat(loaded.date).isEqualTo(updatedIncome.date)
        assertThat(loaded.received).isEqualTo(updatedIncome.received)

    }

}
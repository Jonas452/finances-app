package com.jonas.financesapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.jonas.financesapp.cache.FinancesDatabase
import com.jonas.financesapp.cache.mapper.ExpenseMapper
import com.jonas.financesapp.util.createExpenseItem
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
class ExpenseLocalRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var expenseLocalRepository: ExpenseLocalRepository
    private lateinit var database: FinancesDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FinancesDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        expenseLocalRepository = ExpenseLocalRepositoryImpl(
            database.getExpenseDao(),
            ExpenseMapper(),
        )
    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun insertExpense() = runBlockingTest {
        // Arrange
        val expenseItem = createExpenseItem()
        expenseLocalRepository.insertExpense(expenseItem)

        // Act
        val loaded = expenseLocalRepository.getExpenseById(expenseItem.id)

        // Assert
        assertThat(loaded).isNotNull()
        assertThat(loaded!!.id).isEqualTo(expenseItem.id)
        assertThat(loaded.amount).isEqualTo(expenseItem.amount)
        assertThat(loaded.description).isEqualTo(expenseItem.description)
        assertThat(loaded.date).isEqualTo(expenseItem.date)
        assertThat(loaded.payed).isEqualTo(expenseItem.payed)

    }

    @Test
    fun updateExpense() = runBlockingTest {
        // Arrange
        val expenseItem = createExpenseItem()
        expenseLocalRepository.insertExpense(expenseItem)
        val updatedExpense = createExpenseItem(
            expenseItem.id,
            expenseItem.amount,
            "new description",
            expenseItem.date,
            expenseItem.payed,
        )

        // Act
        expenseLocalRepository.updateExpense(updatedExpense)
        val loaded = expenseLocalRepository.getExpenseById(updatedExpense.id)

        // Assert
        assertThat(loaded).isNotNull()
        assertThat(loaded!!.id).isEqualTo(updatedExpense.id)
        assertThat(loaded.amount).isEqualTo(updatedExpense.amount)
        assertThat(loaded.description).isEqualTo(updatedExpense.description)
        assertThat(loaded.date).isEqualTo(updatedExpense.date)
        assertThat(loaded.payed).isEqualTo(updatedExpense.payed)

    }

}
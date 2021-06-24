package com.jonas.financesapp.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.jonas.financesapp.cache.FinancesDatabase
import com.jonas.financesapp.util.createExpenseEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Dao Test created just for the sake of TDD practice
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ExpenseDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FinancesDatabase
    private lateinit var expenseDao: ExpenseDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FinancesDatabase::class.java
        ).build()
        expenseDao = database.getExpenseDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertExpense_getById_returnsExpense() = runBlockingTest {
        // Arrange
        val expenseEntity = createExpenseEntity()
        expenseDao.insertExpense(expenseEntity)

        // Act
        val expenseInserted = expenseDao.getExpenseById(expenseEntity.id)

        // Assert
        assertThat(expenseInserted).isNotNull()
        assertThat(expenseInserted!!.id).isEqualTo(expenseEntity.id)
        assertThat(expenseInserted.amount).isEqualTo(expenseEntity.amount)
        assertThat(expenseInserted.description).isEqualTo(expenseEntity.description)
        assertThat(expenseInserted.date).isEqualTo(expenseEntity.date)
        assertThat(expenseInserted.payed).isEqualTo(expenseEntity.payed)
    }

    @Test
    fun updateExpense_getById_returnsExpense() = runBlockingTest {
        // Arrange
        val expenseEntity = createExpenseEntity()
        expenseDao.insertExpense(expenseEntity)
        val updatedExpense = createExpenseEntity(
            expenseEntity.id,
            expenseEntity.amount,
            "new description",
            expenseEntity.date,
            expenseEntity.payed,
        )

        // Act
        expenseDao.updateExpense(updatedExpense)
        val expenseUpdated = expenseDao.getExpenseById(updatedExpense.id)

        // Assert
        assertThat(expenseUpdated).isNotNull()
        assertThat(expenseUpdated!!.id).isEqualTo(updatedExpense.id)
        assertThat(expenseUpdated.amount).isEqualTo(updatedExpense.amount)
        assertThat(expenseUpdated.description).isEqualTo(updatedExpense.description)
        assertThat(expenseUpdated.date).isEqualTo(updatedExpense.date)
        assertThat(expenseUpdated.payed).isEqualTo(updatedExpense.payed)
    }

}
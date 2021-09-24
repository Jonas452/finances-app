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
        val loaded = expenseDao.getExpenseById(expenseEntity.id)

        // Assert
        assertThat(loaded).isNotNull()
        assertThat(loaded!!.id).isEqualTo(expenseEntity.id)
        assertThat(loaded.amount).isEqualTo(expenseEntity.amount)
        assertThat(loaded.description).isEqualTo(expenseEntity.description)
        assertThat(loaded.date).isEqualTo(expenseEntity.date)
        assertThat(loaded.paid).isEqualTo(expenseEntity.paid)
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
            expenseEntity.paid,
        )

        // Act
        expenseDao.updateExpense(updatedExpense)
        val loaded = expenseDao.getExpenseById(updatedExpense.id)

        // Assert
        assertThat(loaded).isNotNull()
        assertThat(loaded!!.id).isEqualTo(updatedExpense.id)
        assertThat(loaded.amount).isEqualTo(updatedExpense.amount)
        assertThat(loaded.description).isEqualTo(updatedExpense.description)
        assertThat(loaded.date).isEqualTo(updatedExpense.date)
        assertThat(loaded.paid).isEqualTo(updatedExpense.paid)
    }

}
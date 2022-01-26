package com.jonas.financesapp.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.jonas.financesapp.data.local.FinancesDatabase
import com.jonas.financesapp.util.createIncomeEntity
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
class IncomeDaoTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FinancesDatabase
    private lateinit var incomeDao: IncomeDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FinancesDatabase::class.java
        ).build()
        incomeDao = database.getIncomeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertIncome_getById_returnsIncome() = runBlockingTest {
        // Arrange
        val incomeEntity = createIncomeEntity()
        incomeDao.insertIncome(incomeEntity)

        // Act
        val loaded = incomeDao.getIncomeById(incomeEntity.id)

        // Assert
        assertThat(loaded).isNotNull()
        assertThat(loaded!!.id).isEqualTo(incomeEntity.id)
        assertThat(loaded.amount).isEqualTo(incomeEntity.amount)
        assertThat(loaded.description).isEqualTo(incomeEntity.description)
        assertThat(loaded.date).isEqualTo(incomeEntity.date)
        assertThat(loaded.received).isEqualTo(incomeEntity.received)
    }

    @Test
    fun updateIncome_getById_returnsIncome() = runBlockingTest {
        // Arrange
        val incomeEntity = createIncomeEntity()
        incomeDao.insertIncome(incomeEntity)
        val updatedIncome = createIncomeEntity(
            incomeEntity.id,
            incomeEntity.amount,
            "new description",
            incomeEntity.date,
            incomeEntity.received,
        )
        // Act
        incomeDao.updateIncome(updatedIncome)
        val loaded = incomeDao.getIncomeById(updatedIncome.id)

        // Assert
        assertThat(loaded).isNotNull()
        assertThat(loaded!!.id).isEqualTo(updatedIncome.id)
        assertThat(loaded.amount).isEqualTo(updatedIncome.amount)
        assertThat(loaded.description).isEqualTo(updatedIncome.description)
        assertThat(loaded.date).isEqualTo(updatedIncome.date)
        assertThat(loaded.received).isEqualTo(updatedIncome.received)
    }

}
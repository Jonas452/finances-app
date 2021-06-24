package com.jonas.financesapp.cache.dao

import androidx.room.*
import com.jonas.financesapp.cache.entity.ExpenseEntity
import com.jonas.financesapp.cache.entity.ExpenseEntity.Companion.TABLE_NAME

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(updatedExpense: ExpenseEntity): Int

    @Query(
        """
            SELECT * FROM $TABLE_NAME WHERE id = :id
        """
    )
    suspend fun getExpenseById(id: Long): ExpenseEntity

}
package com.jonas.financesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jonas.financesapp.data.local.entity.ExpenseEntity
import com.jonas.financesapp.data.local.entity.ExpenseEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow
import java.util.*

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
    suspend fun getExpenseById(id: UUID): ExpenseEntity?

    @Query(
        """
            SELECT SUM(amount) FROM $TABLE_NAME
        """
    )
    fun getSumAllExpense(): Flow<Double?>

}
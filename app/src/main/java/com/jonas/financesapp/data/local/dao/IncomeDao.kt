package com.jonas.financesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jonas.financesapp.data.local.entity.IncomeEntity
import com.jonas.financesapp.data.local.entity.IncomeEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface IncomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(incomeEntity: IncomeEntity)

    @Update
    suspend fun updateIncome(updatedIncome: IncomeEntity): Int

    @Query(
        """
        SELECT * FROM $TABLE_NAME WHERE id = :id
    """
    )
    suspend fun getIncomeById(id: UUID): IncomeEntity?

    @Query(
        """
            SELECT SUM(amount) FROM $TABLE_NAME
        """
    )
    fun getSumAllIncome(): Flow<Double?>

}
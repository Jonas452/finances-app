package com.jonas.financesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jonas.financesapp.data.local.entity.IncomeExpenseView
import com.jonas.financesapp.data.local.entity.IncomeExpenseView.Companion.VIEW_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeExpenseViewDao {

    @Query(
        """
        SELECT * FROM $VIEW_NAME
    """
    )
    fun getAllIncomeExpense(): Flow<List<IncomeExpenseView>>

}
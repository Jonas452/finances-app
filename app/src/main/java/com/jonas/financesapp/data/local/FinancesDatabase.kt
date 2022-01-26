package com.jonas.financesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jonas.financesapp.data.local.converter.BigDecimalConverter
import com.jonas.financesapp.data.local.converter.DateConverter
import com.jonas.financesapp.data.local.converter.IncomeExpenseTypeConverter
import com.jonas.financesapp.data.local.converter.UUIDConverter
import com.jonas.financesapp.data.local.dao.ExpenseDao
import com.jonas.financesapp.data.local.dao.IncomeDao
import com.jonas.financesapp.data.local.dao.IncomeExpenseViewDao
import com.jonas.financesapp.data.local.entity.ExpenseEntity
import com.jonas.financesapp.data.local.entity.IncomeEntity
import com.jonas.financesapp.data.local.entity.IncomeExpenseView

@Database(
    entities = [
        ExpenseEntity::class,
        IncomeEntity::class,
    ],
    views = [
        IncomeExpenseView::class,
    ],
    version = 2,
)
@TypeConverters(
    value = [
        BigDecimalConverter::class,
        DateConverter::class,
        UUIDConverter::class,
        IncomeExpenseTypeConverter::class,
    ]
)
abstract class FinancesDatabase : RoomDatabase() {

    abstract fun getExpenseDao(): ExpenseDao
    abstract fun getIncomeDao(): IncomeDao
    abstract fun getIncomeExpenseViewDao(): IncomeExpenseViewDao

    companion object {
        private const val DB_NAME = "finances.db"

        @JvmStatic
        fun newInstance(context: Context) = Room.databaseBuilder(
            context,
            FinancesDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}
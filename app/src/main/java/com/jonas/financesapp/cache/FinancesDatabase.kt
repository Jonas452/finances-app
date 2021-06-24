package com.jonas.financesapp.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jonas.financesapp.cache.dao.ExpenseDao
import com.jonas.financesapp.cache.dao.IncomeDao
import com.jonas.financesapp.cache.entity.ExpenseEntity
import com.jonas.financesapp.cache.entity.IncomeEntity

@Database(
    entities = [
        ExpenseEntity::class,
        IncomeEntity::class,
    ],
    version = 1,
)
abstract class FinancesDatabase : RoomDatabase() {

    abstract fun getExpenseDao(): ExpenseDao
    abstract fun getIncomeDao(): IncomeDao

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
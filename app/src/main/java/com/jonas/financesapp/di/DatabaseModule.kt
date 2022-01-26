package com.jonas.financesapp.di

import android.content.Context
import com.jonas.financesapp.data.local.FinancesDatabase
import com.jonas.financesapp.data.repository.ExpenseLocalRepository
import com.jonas.financesapp.data.repository.ExpenseLocalRepositoryImpl
import com.jonas.financesapp.data.repository.IncomeExpenseLocalRepository
import com.jonas.financesapp.data.repository.IncomeExpenseLocalRepositoryImpl
import com.jonas.financesapp.data.repository.IncomeLocalRepository
import com.jonas.financesapp.data.repository.IncomeLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesFinancesDatabase(@ApplicationContext context: Context) =
        FinancesDatabase.newInstance(context)

    @Provides
    @Singleton
    fun providesIncomeDao(db: FinancesDatabase) = db.getIncomeDao()

    @Provides
    @Singleton
    fun providesExpenseDao(db: FinancesDatabase) = db.getExpenseDao()

    @Provides
    @Singleton
    fun providesIncomeExpenseViewDao(db: FinancesDatabase) = db.getIncomeExpenseViewDao()

    @Provides
    @Singleton
    fun providesIncomeLocalRepository(impl: IncomeLocalRepositoryImpl): IncomeLocalRepository =
        impl

    @Provides
    @Singleton
    fun providesExpenseLocalRepository(impl: ExpenseLocalRepositoryImpl): ExpenseLocalRepository =
        impl

    @Provides
    @Singleton
    fun providesIncomeExpenseLocalRepository(impl: IncomeExpenseLocalRepositoryImpl): IncomeExpenseLocalRepository =
        impl

}
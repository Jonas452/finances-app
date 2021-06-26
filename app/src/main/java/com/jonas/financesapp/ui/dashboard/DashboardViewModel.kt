package com.jonas.financesapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jonas.financesapp.usecase.incomeexpense.GetAllIncomeExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAllIncomeExpenseUseCase: GetAllIncomeExpenseUseCase,
) : ViewModel() {

    val incomeExpenseItems by lazy { getAllIncomeExpenseUseCase().asLiveData() }

}
package com.jonas.financesapp.ui.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.financesapp.model.IncomeExpenseItem
import com.jonas.financesapp.model.IncomeExpenseType
import com.jonas.financesapp.usecase.expense.GetSumAllExpenseUseCase
import com.jonas.financesapp.usecase.income.GetSumAllIncomeUseCase
import com.jonas.financesapp.usecase.incomeexpense.GetAllIncomeExpenseUseCase
import com.jonas.financesapp.util.Constants.DEFAULT_MONEY_VALUE
import com.jonas.financesapp.util.formatToMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    getAllIncomeExpenseUseCase: GetAllIncomeExpenseUseCase,
    getSumAllExpenseUseCase: GetSumAllExpenseUseCase,
    getSumAllIncomeUseCase: GetSumAllIncomeUseCase,
) : ViewModel() {

    val incomeExpenseItems: StateFlow<List<IncomeExpenseItem>> =
        getAllIncomeExpenseUseCase().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList(),
        )

    private val _dashboardEvent = MutableStateFlow<DashboardUIState>(DashboardUIState.Empty)
    val dashboardEvent: StateFlow<DashboardUIState>
        get() = _dashboardEvent

    private val _income: Flow<Double> = getSumAllIncomeUseCase()
    private val _expense: Flow<Double> = getSumAllExpenseUseCase()

    private val _balance by lazy {
        combine(_income, _expense) { inc, exp ->
            inc.minus(exp)
        }
    }

    val income: StateFlow<String>
        get() = _income.map { it.formatToMoney(context) }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            DEFAULT_MONEY_VALUE.formatToMoney(context),
        )

    val expense: StateFlow<String>
        get() = _expense.map { it.formatToMoney(context) }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            DEFAULT_MONEY_VALUE.formatToMoney(context),
        )

    val balance: StateFlow<String>
        get() = _balance.map { it.formatToMoney(context) }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            DEFAULT_MONEY_VALUE.formatToMoney(context),
        )

    fun openIncomeExpense(incomeExpenseItem: IncomeExpenseItem) {
        when (incomeExpenseItem.type) {
            IncomeExpenseType.INCOME -> _dashboardEvent.value =
                DashboardUIState.OpenIncomeCreateUpdateFragment(incomeExpenseItem.id)
            IncomeExpenseType.EXPENSE -> _dashboardEvent.value =
                DashboardUIState.OpenExpenseCreateUpdateFragment(incomeExpenseItem.id)
        }
    }
}
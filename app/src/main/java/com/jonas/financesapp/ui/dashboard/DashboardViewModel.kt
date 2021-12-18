package com.jonas.financesapp.ui.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.financesapp.domain.model.IncomeExpenseItem
import com.jonas.financesapp.domain.model.IncomeExpenseType
import com.jonas.financesapp.domain.usecase.expense.GetSumAllExpenseUseCase
import com.jonas.financesapp.domain.usecase.income.GetSumAllIncomeUseCase
import com.jonas.financesapp.domain.usecase.incomeexpense.GetAllIncomeExpenseUseCase
import com.jonas.financesapp.util.Constants.DEFAULT_MONEY_VALUE
import com.jonas.financesapp.util.formatToMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    private val _dashboardEvent = MutableSharedFlow<DashboardUIState>(replay = 0)
    val dashboardEvent: SharedFlow<DashboardUIState> = _dashboardEvent

    private val _income: Flow<Double> = getSumAllIncomeUseCase()
    private val _expense: Flow<Double> = getSumAllExpenseUseCase()

    private val _balance by lazy {
        combine(_income, _expense) { inc, exp ->
            inc.minus(exp)
        }
    }

    val income: StateFlow<String> = _income.map { it.formatToMoney(context) }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        DEFAULT_MONEY_VALUE.formatToMoney(context),
    )

    val expense: StateFlow<String> = _expense.map { it.formatToMoney(context) }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        DEFAULT_MONEY_VALUE.formatToMoney(context),
    )

    val balance: StateFlow<String> = _balance.map { it.formatToMoney(context) }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        DEFAULT_MONEY_VALUE.formatToMoney(context),
    )

    fun openIncomeExpense(incomeExpenseItem: IncomeExpenseItem) {
        viewModelScope.launch {
            when (incomeExpenseItem.type) {
                IncomeExpenseType.INCOME -> _dashboardEvent.emit(
                    DashboardUIState.OpenIncomeCreateUpdateFragment(incomeExpenseItem.id)
                )
                IncomeExpenseType.EXPENSE -> _dashboardEvent.emit(
                    DashboardUIState.OpenExpenseCreateUpdateFragment(
                        incomeExpenseItem.id
                    )
                )
            }
        }
    }
}
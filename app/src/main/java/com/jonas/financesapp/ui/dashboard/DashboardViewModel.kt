package com.jonas.financesapp.ui.dashboard

import android.content.Context
import androidx.lifecycle.*
import com.jonas.financesapp.model.IncomeExpenseItem
import com.jonas.financesapp.model.IncomeExpenseType
import com.jonas.financesapp.usecase.expense.GetSumAllExpenseUseCase
import com.jonas.financesapp.usecase.income.GetSumAllIncomeUseCase
import com.jonas.financesapp.usecase.incomeexpense.GetAllIncomeExpenseUseCase
import com.jonas.financesapp.util.LiveDataUtils
import com.jonas.financesapp.util.formatToMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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

    private val _income: LiveData<Double> =
        getSumAllIncomeUseCase().asLiveData()
    val income: LiveData<String>
        get() = _income.map { it.formatToMoney(context) }

    private val _expense: LiveData<Double> =
        getSumAllExpenseUseCase().asLiveData()
    val expense: LiveData<String>
        get() = _expense.map { it.formatToMoney(context) }

    private val _balance by lazy {
        LiveDataUtils.combine(_income, _expense) { inc, exp ->
            (inc ?: 0.0).minus(exp ?: 0.0)
        }
    }
    val balance: LiveData<String>
        get() = _balance.map { it.formatToMoney(context) }

    fun openIncomeExpense(incomeExpenseItem: IncomeExpenseItem) {
        when (incomeExpenseItem.type) {
            IncomeExpenseType.INCOME -> _dashboardEvent.value =
                DashboardUIState.OpenIncomeCreateUpdateFragment(incomeExpenseItem.id)
            IncomeExpenseType.EXPENSE -> _dashboardEvent.value =
                DashboardUIState.OpenExpenseCreateUpdateFragment(incomeExpenseItem.id)
        }
    }
}
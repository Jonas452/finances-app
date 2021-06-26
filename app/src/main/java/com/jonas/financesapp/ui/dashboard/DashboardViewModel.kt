package com.jonas.financesapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jonas.financesapp.model.IncomeExpenseItem
import com.jonas.financesapp.model.IncomeExpenseType
import com.jonas.financesapp.usecase.expense.GetSumAllExpenseUseCase
import com.jonas.financesapp.usecase.income.GetSumAllIncomeUseCase
import com.jonas.financesapp.usecase.incomeexpense.GetAllIncomeExpenseUseCase
import com.jonas.financesapp.util.Event
import com.jonas.financesapp.util.roundTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getAllIncomeExpenseUseCase: GetAllIncomeExpenseUseCase,
    getSumAllExpenseUseCase: GetSumAllExpenseUseCase,
    getSumAllIncomeUseCase: GetSumAllIncomeUseCase,
) : ViewModel() {

    val incomeExpenseItems = getAllIncomeExpenseUseCase().asLiveData()

    private val _dashboardEvent = MutableLiveData<Event<DashboardEvent>>()
    val dashboardEvent: LiveData<Event<DashboardEvent>>
        get() = _dashboardEvent

    val income = getSumAllIncomeUseCase().map { it.roundTo(2) }.asLiveData()
    val expense = getSumAllExpenseUseCase().map { it.roundTo(2) }.asLiveData()

    fun openIncomeExpense(incomeExpenseItem: IncomeExpenseItem) {
        when (incomeExpenseItem.type) {
            IncomeExpenseType.INCOME -> _dashboardEvent.value =
                Event(DashboardEvent.OpenIncomeCreateUpdateFragment(incomeExpenseItem.id))
            IncomeExpenseType.EXPENSE -> _dashboardEvent.value =
                Event(DashboardEvent.OpenExpenseCreateUpdateFragment(incomeExpenseItem.id))
        }
    }

    sealed class DashboardEvent {
        class OpenIncomeCreateUpdateFragment(val id: UUID) : DashboardEvent()
        class OpenExpenseCreateUpdateFragment(val id: UUID) : DashboardEvent()
    }

}
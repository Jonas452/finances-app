package com.jonas.financesapp.ui.dashboard

import android.content.Context
import androidx.lifecycle.*
import com.jonas.financesapp.R
import com.jonas.financesapp.model.IncomeExpenseItem
import com.jonas.financesapp.model.IncomeExpenseType
import com.jonas.financesapp.usecase.expense.GetSumAllExpenseUseCase
import com.jonas.financesapp.usecase.income.GetSumAllIncomeUseCase
import com.jonas.financesapp.usecase.incomeexpense.GetAllIncomeExpenseUseCase
import com.jonas.financesapp.util.Event
import com.jonas.financesapp.util.LiveDataUtils
import com.jonas.financesapp.util.roundTo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    getAllIncomeExpenseUseCase: GetAllIncomeExpenseUseCase,
    getSumAllExpenseUseCase: GetSumAllExpenseUseCase,
    getSumAllIncomeUseCase: GetSumAllIncomeUseCase,
) : ViewModel() {

    val incomeExpenseItems = getAllIncomeExpenseUseCase().asLiveData()

    private val _dashboardEvent = MutableLiveData<Event<DashboardEvent>>()
    val dashboardEvent: LiveData<Event<DashboardEvent>>
        get() = _dashboardEvent

    private val _income: LiveData<Double> =
        getSumAllIncomeUseCase().asLiveData()
    val income: LiveData<String>
        get() = _income.map { context.getString(R.string.sign_money, it.roundTo(2).toString()) }

    private val _expense: LiveData<Double> =
        getSumAllExpenseUseCase().asLiveData()
    val expense: LiveData<String>
        get() = _expense.map { context.getString(R.string.sign_money, it.roundTo(2).toString()) }

    private val _balance by lazy {
        LiveDataUtils.combine(_income, _expense) { inc, exp ->
            (inc ?: 0.0).minus(exp ?: 0.0)
        }
    }
    val balance: LiveData<String>
        get() = _balance.map { context.getString(R.string.sign_money, it.roundTo(2).toString()) }


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
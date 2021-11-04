package com.jonas.financesapp.ui.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.financesapp.di.IOContext
import com.jonas.financesapp.model.ExpenseItem
import com.jonas.financesapp.usecase.expense.GetExpenseByIdUseCase
import com.jonas.financesapp.usecase.expense.InsertExpenseUseCase
import com.jonas.financesapp.usecase.expense.UpdateExpenseUseCase
import com.jonas.financesapp.util.Constants.EMPTY_STRING
import com.jonas.financesapp.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ExpenseCreateUpdateViewModel @Inject constructor(
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    @IOContext private val ioContext: CoroutineContext,
) : ViewModel() {

    // Exposed MutableLiveData for two-way data binding
    val amount = MutableStateFlow(EMPTY_STRING)
    val description = MutableStateFlow(EMPTY_STRING)
    val date = MutableStateFlow(EMPTY_STRING)
    val paid = MutableStateFlow(false)

    private val _expenseCreateUpdateEvent =
        MutableStateFlow<ExpenseCreateUpdateUIState>(ExpenseCreateUpdateUIState.Empty)
    val expenseCreateUpdateEvent: StateFlow<ExpenseCreateUpdateUIState>
        get() = _expenseCreateUpdateEvent

    private var expenseId: String? = null
    private var isNewExpense = true

    init {
        paid.value = false
    }

    fun saveExpense() = viewModelScope.launch(ioContext) {
        try {
            val currentAmount = amount.value
            val currentDescription = description.value
            val currentDate = date.value
            val currentPaid = paid.value

            if (currentAmount.isNotEmpty() &&
                currentDescription.isNotEmpty() &&
                currentDate.isNotEmpty()
            ) {
                val expenseItem = ExpenseItem(
                    amount = BigDecimal(currentAmount),
                    description = currentDescription,
                    date = DateUtils.formatDate(
                        currentDate,
                        DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME
                    ),
                    paid = currentPaid,
                )

                val currentExpenseId = expenseId
                if (isNewExpense && currentExpenseId.isNullOrEmpty()) {
                    insertExpenseUseCase(expenseItem)
                } else {
                    expenseItem.id = UUID.fromString(currentExpenseId)
                    updateExpenseUseCase(expenseItem)
                }
                setSuccessState()
            } else {
                _expenseCreateUpdateEvent.value =
                    ExpenseCreateUpdateUIState.InvalidData
            }
        } catch (e: Exception) {
            e.printStackTrace() // Should be a log
            setErrorState()
        }
    }

    fun loadExpenses(id: String) = viewModelScope.launch {
        expenseId = id
        isNewExpense = false
        val expense = getExpenseByIdUseCase(UUID.fromString(id))
        if (expense != null) {
            amount.value = expense.amount.toString()
            description.value = expense.description
            date.value =
                DateUtils.formatDate(
                    expense.date,
                    DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME
                )
            paid.value = expense.paid
        }
    }


    private fun setSuccessState() {
        _expenseCreateUpdateEvent.value =
            if (isNewExpense)
                ExpenseCreateUpdateUIState.SuccessInserting
            else
                ExpenseCreateUpdateUIState.SuccessUpdating
    }

    private fun setErrorState() {
        _expenseCreateUpdateEvent.value =
            if (isNewExpense)
                ExpenseCreateUpdateUIState.ErrorInserting
            else
                ExpenseCreateUpdateUIState.ErrorUpdating
    }
}
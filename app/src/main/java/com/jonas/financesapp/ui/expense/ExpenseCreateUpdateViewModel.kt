package com.jonas.financesapp.ui.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.financesapp.di.IOContext
import com.jonas.financesapp.model.ExpenseItem
import com.jonas.financesapp.usecase.expense.InsertExpenseUseCase
import com.jonas.financesapp.usecase.expense.UpdateExpenseUseCase
import com.jonas.financesapp.util.DateUtils
import com.jonas.financesapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ExpenseCreateUpdateViewModel @Inject constructor(
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    @IOContext private val ioContext: CoroutineContext,
) : ViewModel() {

    // Exposed MutableLiveData for two-way data binding
    val amount = MutableLiveData<Double>()
    val description = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val paid = MutableLiveData<Boolean>()

    private val _expenseCreateUpdateEvent = MutableLiveData<Event<ExpenseCreateUpdateState>>()
    val expenseCreateUpdateEvent: LiveData<Event<ExpenseCreateUpdateState>> =
        _expenseCreateUpdateEvent

    private var expenseId: String? = null
    private var isNewExpense = true

    fun saveExpense() = viewModelScope.launch(ioContext) {
        try {
            val currentAmount = amount.value
            val currentDescription = description.value
            val currentDate = date.value
            val currentPaid = paid.value

            if (currentAmount != null &&
                currentDescription != null &&
                currentDate != null &&
                currentPaid != null
            ) {
                val expenseItem = ExpenseItem(
                    amount = BigDecimal(currentAmount),
                    description = currentDescription,
                    date = DateUtils.formatDate(
                        currentDate,
                        DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME
                    ),
                    payed = currentPaid,
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
                _expenseCreateUpdateEvent.postValue(
                    Event(ExpenseCreateUpdateState.InvalidData)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace() // Should be a log
            setErrorState()
        }
    }

    private fun setSuccessState() {
        val state =
            if (isNewExpense) ExpenseCreateUpdateState.SuccessInserting else ExpenseCreateUpdateState.SuccessUpdating
        _expenseCreateUpdateEvent.postValue(Event(state))
    }

    private fun setErrorState() {
        val state =
            if (isNewExpense) ExpenseCreateUpdateState.ErrorInserting else ExpenseCreateUpdateState.ErrorUpdating
        _expenseCreateUpdateEvent.postValue(Event(state))
    }

    sealed class ExpenseCreateUpdateState {
        object SuccessInserting : ExpenseCreateUpdateState()
        object ErrorInserting : ExpenseCreateUpdateState()
        object SuccessUpdating : ExpenseCreateUpdateState()
        object ErrorUpdating : ExpenseCreateUpdateState()
        object InvalidData : ExpenseCreateUpdateState()
    }

}
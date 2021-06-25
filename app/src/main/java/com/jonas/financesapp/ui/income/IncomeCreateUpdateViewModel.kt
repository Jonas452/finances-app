package com.jonas.financesapp.ui.income

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.financesapp.di.IOContext
import com.jonas.financesapp.model.IncomeItem
import com.jonas.financesapp.usecase.income.InsertIncomeUseCase
import com.jonas.financesapp.usecase.income.UpdateIncomeUseCase
import com.jonas.financesapp.util.DateUtils
import com.jonas.financesapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class IncomeCreateUpdateViewModel @Inject constructor(
    private val insertIncomeUseCase: InsertIncomeUseCase,
    private val updateIncomeUseCase: UpdateIncomeUseCase,
    @IOContext private val ioContext: CoroutineContext,
) : ViewModel() {

    // Exposed MutableLiveData for two-way data binding
    val amount = MutableLiveData<Double>()
    val description = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val received = MutableLiveData<Boolean>()

    private val _incomeCreateUpdateEvent = MutableLiveData<Event<IncomeCreateUpdateState>>()
    val incomeCreateUpdateEvent: LiveData<Event<IncomeCreateUpdateState>> = _incomeCreateUpdateEvent

    private var incomeId: String? = null
    private var isNewIncome = true

    fun saveIncome() = viewModelScope.launch(ioContext) {
        try {
            val currentAmount = amount.value
            val currentDescription = description.value
            val currentDate = date.value
            val currentReceived = received.value

            if (currentAmount != null &&
                currentDescription != null &&
                currentDate != null &&
                currentReceived != null
            ) {
                val incomeItem = IncomeItem(
                    amount = BigDecimal(currentAmount),
                    description = currentDescription,
                    date = DateUtils.formatDate(
                        currentDate,
                        DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME
                    ),
                    received = currentReceived,
                )

                val currentIncomeId = incomeId
                if (isNewIncome && currentIncomeId.isNullOrEmpty()) {
                    insertIncomeUseCase(incomeItem)
                } else {
                    incomeItem.id = UUID.fromString(currentIncomeId)
                    updateIncomeUseCase(incomeItem)
                }
                setSuccessState()
            } else {
                _incomeCreateUpdateEvent.postValue(
                    Event(IncomeCreateUpdateState.InvalidData)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace() // Should be a log
            setErrorState()
        }
    }

    private fun setSuccessState() {
        val state =
            if (isNewIncome) IncomeCreateUpdateState.SuccessInserting else IncomeCreateUpdateState.SuccessUpdating
        _incomeCreateUpdateEvent.postValue(Event(state))
    }

    private fun setErrorState() {
        val state =
            if (isNewIncome) IncomeCreateUpdateState.ErrorInserting else IncomeCreateUpdateState.ErrorUpdating
        _incomeCreateUpdateEvent.postValue(Event(state))
    }

    sealed class IncomeCreateUpdateState {
        object SuccessInserting : IncomeCreateUpdateState()
        object ErrorInserting : IncomeCreateUpdateState()
        object SuccessUpdating : IncomeCreateUpdateState()
        object ErrorUpdating : IncomeCreateUpdateState()
        object InvalidData : IncomeCreateUpdateState()
    }

}
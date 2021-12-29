package com.jonas.financesapp.ui.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.financesapp.di.IOContext
import com.jonas.financesapp.domain.model.IncomeItem
import com.jonas.financesapp.domain.usecase.income.GetIncomeByIdUseCase
import com.jonas.financesapp.domain.usecase.income.InsertIncomeUseCase
import com.jonas.financesapp.domain.usecase.income.UpdateIncomeUseCase
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
class IncomeCreateUpdateViewModel @Inject constructor(
    private val insertIncomeUseCase: InsertIncomeUseCase,
    private val updateIncomeUseCase: UpdateIncomeUseCase,
    private val getIncomeByIdUseCase: GetIncomeByIdUseCase,
    @IOContext private val ioContext: CoroutineContext,
) : ViewModel() {

    val amount = MutableStateFlow(EMPTY_STRING)
    val description = MutableStateFlow(EMPTY_STRING)
    val date = MutableStateFlow(EMPTY_STRING)
    val received = MutableStateFlow(false)

    private val _incomeCreateUpdateEvent =
        MutableStateFlow<IncomeCreateUpdateUIState>(IncomeCreateUpdateUIState.Empty)
    val incomeCreateUpdateEvent: StateFlow<IncomeCreateUpdateUIState> = _incomeCreateUpdateEvent

    private var incomeId: String? = null
    private var isNewIncome = true

    fun saveIncome() = viewModelScope.launch(ioContext) {
        try {
            val currentAmount = amount.value
            val currentDescription = description.value
            val currentDate = date.value
            val currentReceived = received.value

            if (currentAmount.isNotEmpty() &&
                currentDescription.isNotEmpty() &&
                currentDate.isNotEmpty()
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
                _incomeCreateUpdateEvent.value =
                    IncomeCreateUpdateUIState.InvalidData
            }
        } catch (e: Exception) {
            e.printStackTrace() // Should be a log
            setErrorState()
        }
    }


    fun loadIncome(id: String) = viewModelScope.launch(ioContext) {
        incomeId = id
        isNewIncome = false
        val income = getIncomeByIdUseCase(UUID.fromString(id))
        if (income != null) {
            amount.value = income.amount.toString()
            description.value = income.description
            date.value =
                DateUtils.formatDate(
                    income.date,
                    DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME
                )
            received.value = income.received
        }
    }

    private fun setSuccessState() {
        _incomeCreateUpdateEvent.value =
            if (isNewIncome)
                IncomeCreateUpdateUIState.SuccessInserting
            else
                IncomeCreateUpdateUIState.SuccessUpdating
    }

    private fun setErrorState() {
        _incomeCreateUpdateEvent.value =
            if (isNewIncome)
                IncomeCreateUpdateUIState.ErrorInserting
            else
                IncomeCreateUpdateUIState.ErrorUpdating
    }
}
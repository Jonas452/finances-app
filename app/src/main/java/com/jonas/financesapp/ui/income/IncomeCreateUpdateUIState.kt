package com.jonas.financesapp.ui.income

sealed class IncomeCreateUpdateUIState {
    object SuccessInserting : IncomeCreateUpdateUIState()
    object ErrorInserting : IncomeCreateUpdateUIState()
    object SuccessUpdating : IncomeCreateUpdateUIState()
    object ErrorUpdating : IncomeCreateUpdateUIState()
    object InvalidData : IncomeCreateUpdateUIState()
    object Empty : IncomeCreateUpdateUIState()
}

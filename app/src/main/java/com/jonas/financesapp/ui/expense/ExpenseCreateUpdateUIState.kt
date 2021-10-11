package com.jonas.financesapp.ui.expense

sealed class ExpenseCreateUpdateUIState {
    object SuccessInserting : ExpenseCreateUpdateUIState()
    object ErrorInserting : ExpenseCreateUpdateUIState()
    object SuccessUpdating : ExpenseCreateUpdateUIState()
    object ErrorUpdating : ExpenseCreateUpdateUIState()
    object InvalidData : ExpenseCreateUpdateUIState()
    object Empty : ExpenseCreateUpdateUIState()
}
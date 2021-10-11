package com.jonas.financesapp.ui.dashboard

import java.util.*

sealed class DashboardUIState {
    class OpenIncomeCreateUpdateFragment(val id: UUID) : DashboardUIState()
    class OpenExpenseCreateUpdateFragment(val id: UUID) : DashboardUIState()
    object Empty : DashboardUIState()
}

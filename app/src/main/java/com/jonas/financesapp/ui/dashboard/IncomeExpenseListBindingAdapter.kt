package com.jonas.financesapp.ui.dashboard

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jonas.financesapp.model.IncomeExpenseItem

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<IncomeExpenseItem>?) {
    items?.let {
        (listView.adapter as IncomeExpenseAdapter).submitList(items)
    }
}
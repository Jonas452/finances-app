package com.jonas.financesapp.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jonas.financesapp.databinding.IncomeExpenseItemBinding
import com.jonas.financesapp.model.IncomeExpenseItem

class IncomeExpenseAdapter(private val viewModel: DashboardViewModel) :
    ListAdapter<IncomeExpenseItem, IncomeExpenseAdapter.ViewHolder>(IncomeExpenseDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: IncomeExpenseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: DashboardViewModel, item: IncomeExpenseItem) {

            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IncomeExpenseItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class IncomeExpenseDiffCallback : DiffUtil.ItemCallback<IncomeExpenseItem>() {
    override fun areItemsTheSame(oldItem: IncomeExpenseItem, newItem: IncomeExpenseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: IncomeExpenseItem,
        newItem: IncomeExpenseItem
    ): Boolean {
        return oldItem == newItem
    }
}

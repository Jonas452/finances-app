package com.jonas.financesapp.ui.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jonas.financesapp.databinding.FragmentExpenseCreateUpdateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseCreateUpdateFragment : Fragment() {

    private val viewModel by viewModels<ExpenseCreateUpdateViewModel>()
    private lateinit var binding: FragmentExpenseCreateUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseCreateUpdateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

}
package com.jonas.financesapp.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jonas.financesapp.databinding.FragmentIncomeCreateUpdateBinding

class IncomeCreateUpdateFragment : Fragment() {

    private val viewModel by viewModels<IncomeCreateUpdateViewModel>()
    private lateinit var binding: FragmentIncomeCreateUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeCreateUpdateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

}
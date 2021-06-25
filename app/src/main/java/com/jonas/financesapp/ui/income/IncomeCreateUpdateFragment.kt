package com.jonas.financesapp.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.jonas.financesapp.R
import com.jonas.financesapp.databinding.FragmentIncomeCreateUpdateBinding
import com.jonas.financesapp.util.DateUtils
import com.jonas.financesapp.util.DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME
import com.jonas.financesapp.util.EventObserver
import com.jonas.financesapp.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Date

@AndroidEntryPoint
class IncomeCreateUpdateFragment : Fragment() {

    private val viewModel by viewModels<IncomeCreateUpdateViewModel>()
    private lateinit var binding: FragmentIncomeCreateUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeCreateUpdateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setupListeners()
        setupObservers()

        return binding.root
    }

    private fun setupListeners() {
        binding.dateInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                requireActivity().hideKeyboard()
                showDatePicker()
            }
            binding.dateInput.clearFocus()
        }
    }

    private fun setupObservers() {
        viewModel.incomeCreateUpdateEvent.observe(viewLifecycleOwner, EventObserver { state ->
            when (state) {
                IncomeCreateUpdateViewModel.IncomeCreateUpdateState.ErrorInserting -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        R.string.error_inserting_income,
                        Toast.LENGTH_LONG,
                    ).show()
                }
                IncomeCreateUpdateViewModel.IncomeCreateUpdateState.ErrorUpdating -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        R.string.error_updating_income,
                        Toast.LENGTH_LONG,
                    ).show()
                }
                IncomeCreateUpdateViewModel.IncomeCreateUpdateState.InvalidData -> {
                    //TODO improve this feedback to the user
                    Toast.makeText(
                        requireActivity().applicationContext,
                        R.string.error_fill_required_fields,
                        Toast.LENGTH_LONG,
                    ).show()
                }
                IncomeCreateUpdateViewModel.IncomeCreateUpdateState.SuccessInserting -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        R.string.success_inserting_income,
                        Toast.LENGTH_LONG,
                    ).show()
                    navigateBack()
                }
                IncomeCreateUpdateViewModel.IncomeCreateUpdateState.SuccessUpdating -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        R.string.success_updating_income,
                        Toast.LENGTH_LONG,
                    ).show()
                    navigateBack()
                }
            }
        })
    }

    private fun navigateBack() {
        val action =
            IncomeCreateUpdateFragmentDirections.actionIncomeCreateUpdateFragmentToDashboardFragment()
        findNavController().navigate(action)
    }

    private fun showDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .build()

        datePicker.show(
            (requireActivity() as AppCompatActivity).supportFragmentManager,
            "datePicker"
        )

        datePicker.addOnPositiveButtonClickListener {
            binding.dateInput.setText(
                DateUtils.formatDate(Date(it), DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME)
            )
        }
    }

}
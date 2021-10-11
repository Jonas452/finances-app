package com.jonas.financesapp.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.MaterialFadeThrough
import com.jonas.financesapp.R
import com.jonas.financesapp.databinding.FragmentIncomeCreateUpdateBinding
import com.jonas.financesapp.util.DateUtils
import com.jonas.financesapp.util.DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME
import com.jonas.financesapp.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.sql.Date

@AndroidEntryPoint
class IncomeCreateUpdateFragment : Fragment() {

    private val viewModel by viewModels<IncomeCreateUpdateViewModel>()
    private lateinit var binding: FragmentIncomeCreateUpdateBinding

    private val args: IncomeCreateUpdateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIncomeCreateUpdateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        args.id?.let {
            viewModel.loadIncome(it)
        }

        setupListeners()
        setupObservers()

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

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
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.incomeCreateUpdateEvent.collect { state ->
                    when (state) {
                        IncomeCreateUpdateViewModel.IncomeCreateUpdateState.ErrorInserting -> {
                            showErrorToast(R.string.error_inserting_income)
                        }
                        IncomeCreateUpdateViewModel.IncomeCreateUpdateState.ErrorUpdating -> {
                            showErrorToast(R.string.error_updating_income)
                        }
                        IncomeCreateUpdateViewModel.IncomeCreateUpdateState.InvalidData -> {
                            showErrorToast(R.string.error_fill_required_fields)
                        }
                        IncomeCreateUpdateViewModel.IncomeCreateUpdateState.SuccessInserting -> {
                            navigateBackSuccess(R.string.success_inserting_income)
                        }
                        IncomeCreateUpdateViewModel.IncomeCreateUpdateState.SuccessUpdating -> {
                            navigateBackSuccess(R.string.success_updating_income)
                        }
                        IncomeCreateUpdateViewModel.IncomeCreateUpdateState.Empty -> Any() //does nothing
                    }
                }
            }
        }
    }

    private fun showErrorToast(@StringRes msg: Int) {
        Toast.makeText(
            requireActivity().applicationContext,
            msg,
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun navigateBackSuccess(@StringRes msg: Int) {
        Toast.makeText(
            requireActivity().applicationContext,
            msg,
            Toast.LENGTH_LONG,
        ).show()
        findNavController().popBackStack()
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
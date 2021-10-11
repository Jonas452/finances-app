package com.jonas.financesapp.ui.expense

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
import com.jonas.financesapp.databinding.FragmentExpenseCreateUpdateBinding
import com.jonas.financesapp.util.DateUtils
import com.jonas.financesapp.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.sql.Date

@AndroidEntryPoint
class ExpenseCreateUpdateFragment : Fragment() {

    private val viewModel by viewModels<ExpenseCreateUpdateViewModel>()
    private lateinit var binding: FragmentExpenseCreateUpdateBinding

    private val args: ExpenseCreateUpdateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenseCreateUpdateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        args.id?.let {
            viewModel.loadExpenses(it)
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
                viewModel.expenseCreateUpdateEvent.collect { state ->
                    when (state) {
                        ExpenseCreateUpdateUIState.ErrorInserting -> {
                            showErrorToast(R.string.error_inserting_expense)
                        }
                        ExpenseCreateUpdateUIState.ErrorUpdating -> {
                            showErrorToast(R.string.error_updating_expense)
                        }
                        ExpenseCreateUpdateUIState.InvalidData -> {
                            showErrorToast(R.string.error_fill_required_fields)
                        }
                        ExpenseCreateUpdateUIState.SuccessInserting -> {
                            navigateBackSuccess(R.string.success_inserting_expense)
                        }
                        ExpenseCreateUpdateUIState.SuccessUpdating -> {
                            navigateBackSuccess(R.string.success_updating_expense)
                        }
                        ExpenseCreateUpdateUIState.Empty -> Any() //does nothing
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
                DateUtils.formatDate(Date(it), DateUtils.DAY_MONTH_YEAR_FORMAT_DATE_WITHOUT_TIME)
            )
        }
    }

}
package com.jonas.financesapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jonas.financesapp.R
import com.jonas.financesapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel by viewModels<DashboardViewModel>()
    private lateinit var binding: FragmentDashboardBinding

    private val rotateOpenAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open_anim
        )
    }
    private val rotateCloseAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close_anim
        )
    }
    private val fromBottomAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_anim
        )
    }
    private val toBottomAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_anim
        )
    }

    private var fabIncomeExpenseClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.fabIncomeExpense.setOnClickListener {
            fabIncomeExpenseClicked = !fabIncomeExpenseClicked
            setupFabVisibility()
            setupFabAnimations()
            setupFabClickable()
        }

        binding.fabExpense.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToExpenseCreateUpdateFragment()
            findNavController().navigate(action)
        }

        binding.fabIncome.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToIncomeCreateUpdateFragment()
            findNavController().navigate(action)
        }

    }

    private fun setupFabVisibility() {
        binding.fabExpense.isVisible = fabIncomeExpenseClicked
        binding.fabIncome.isVisible = fabIncomeExpenseClicked
    }

    private fun setupFabAnimations() {
        if (fabIncomeExpenseClicked) {
            binding.fabExpense.startAnimation(fromBottomAnim)
            binding.fabIncome.startAnimation(fromBottomAnim)

            binding.fabIncomeExpense.startAnimation(rotateOpenAnim)
        } else {
            binding.fabExpense.startAnimation(toBottomAnim)
            binding.fabIncome.startAnimation(toBottomAnim)

            binding.fabIncomeExpense.startAnimation(rotateCloseAnim)
        }
    }

    private fun setupFabClickable() {
        binding.fabExpense.isClickable = fabIncomeExpenseClicked
        binding.fabIncome.isClickable = fabIncomeExpenseClicked
    }

}
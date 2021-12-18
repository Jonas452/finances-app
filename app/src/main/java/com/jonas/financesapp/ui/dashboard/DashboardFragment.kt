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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialFadeThrough
import com.jonas.financesapp.R
import com.jonas.financesapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel by viewModels<DashboardViewModel>()
    private lateinit var binding: FragmentDashboardBinding

    private lateinit var listAdapter: IncomeExpenseAdapter

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
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        listAdapter = IncomeExpenseAdapter(viewModel)

        setupViews()
        setupListeners()
        setupObservers()

        enterTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        fabIncomeExpenseClicked = false
    }

    private fun setupViews() {
        binding.incomeExpenseList.adapter = listAdapter
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
                DashboardFragmentDirections.actionDashboardFragmentToExpenseCreateUpdateFragment(
                    null
                )
            findNavController().navigate(action)
        }

        binding.fabIncome.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToIncomeCreateUpdateFragment(null)
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dashboardEvent.collect { state ->
                    when (state) {
                        is DashboardUIState.OpenExpenseCreateUpdateFragment -> openExpenseCreateFragment(
                            state.id
                        )
                        is DashboardUIState.OpenIncomeCreateUpdateFragment -> openIncomeCreateUpdateFragment(
                            state.id
                        )
                    }
                }
            }
        }
    }

    private fun openExpenseCreateFragment(id: UUID) {
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToExpenseCreateUpdateFragment(id.toString())
        findNavController().navigate(action)
    }

    private fun openIncomeCreateUpdateFragment(id: UUID) {
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToIncomeCreateUpdateFragment(id.toString())
        findNavController().navigate(action)
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
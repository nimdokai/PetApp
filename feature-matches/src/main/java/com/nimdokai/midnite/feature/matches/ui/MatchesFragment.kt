package com.nimdokai.midnite.feature.matches.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nimdokai.core_util.navigation.MatchDetailsNavigator
import com.nimdokai.core_util.viewBinding
import com.nimdokai.midnite.core.resources.views.showDefaultErrorDialog
import com.nimdokai.midnite.feature.matches.R
import com.nimdokai.midnite.feature.matches.databinding.FragmentMatchesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

@AndroidEntryPoint
class MatchesFragment : Fragment(R.layout.fragment_matches) {

    private val viewModel by viewModels<AnimalCategoriesViewModel>()
    private val binding by viewBinding(FragmentMatchesBinding::bind)

    private var adapter: MatchesAdapter =
        MatchesAdapter { match -> viewModel.onMatchClicked(match) }

    @Inject
    lateinit var matchDetailsNavigator: MatchDetailsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.onFirstLaunch()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        observeEvent()
        setupViews()
    }

    override fun onDestroyView() {
        lifecycleScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }

    private fun setupViews() {
        binding.matchesRecyclerView.adapter = adapter
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                adapter.submitList(state.categories)
            }
        }
    }

    private fun observeEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is MatchesEvent.NavigateToMatchDetails -> matchDetailsNavigator.open(
                        navController = findNavController(),
                        matchId = event.matchId
                    )
                    is MatchesEvent.ShowError -> requireContext().showDefaultErrorDialog(
                        title = event.title,
                        message = event.message,
                        buttonText = event.buttonText,
                        action = event.action
                    )
                }
            }
        }
    }

}
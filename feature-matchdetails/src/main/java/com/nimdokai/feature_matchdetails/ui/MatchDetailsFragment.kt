package com.nimdokai.feature_matchdetails.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.nimdokai.core_util.viewBinding
import com.nimdokai.midnite.core.resources.views.showDefaultErrorDialog
import com.nimdokai.midnite.feature.matchdetails.R
import com.nimdokai.midnite.feature.matchdetails.databinding.FragmentMatchDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelChildren

@AndroidEntryPoint
class MatchDetailsFragment : Fragment(R.layout.fragment_match_details) {

    private val viewModel by viewModels<MatchDetailsViewModel>()
    private val binding by viewBinding(FragmentMatchDetailsBinding::bind)

    private val adapter = MatchDetailsAdapter()

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
        binding.markets.adapter = adapter
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                state.matchDetailsUI?.run {
                    binding.startTime.text = startTime
                    binding.name.text = name
                    binding.awayTeamImage.load(awayTeam.imageUrl)
                    binding.homeTeamImage.load(homeTeam.imageUrl)
                    adapter.submitList(detailsItems)
                }
            }
        }
    }

    private fun observeEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is MatchDetailsEvent.ShowError -> requireContext().showDefaultErrorDialog(
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
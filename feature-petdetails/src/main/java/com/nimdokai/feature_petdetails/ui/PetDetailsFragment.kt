package com.nimdokai.feature_petdetails.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nimdokai.core_util.viewBinding
import com.nimdokai.pet.core.resources.views.showDefaultErrorDialog
import com.nimdokai.pet.feature.petdetails.R
import com.nimdokai.pet.feature.petdetails.databinding.FragmentPetDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelChildren

@AndroidEntryPoint
class PetDetailsFragment : Fragment(R.layout.fragment_pet_details) {

    private val viewModel by viewModels<PetDetailsViewModel>()
    private val binding by viewBinding(FragmentPetDetailsBinding::bind)

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

    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                state.petDetailsUiModel?.run {
                    TODO()
                }
            }
        }
    }

    private fun observeEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is PetDetailsEvent.ShowError -> requireContext().showDefaultErrorDialog(
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
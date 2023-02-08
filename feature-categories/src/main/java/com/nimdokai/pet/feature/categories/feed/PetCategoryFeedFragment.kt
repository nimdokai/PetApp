package com.nimdokai.pet.feature.categories.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.nimdokai.core_util.navigation.PetDetailsNavigator
import com.nimdokai.core_util.viewBinding
import com.nimdokai.pet.core.resources.views.showDefaultErrorDialog
import com.nimdokai.pet.feature.categories.R
import com.nimdokai.pet.feature.categories.databinding.FragmentCategoryFeedBinding
import com.nimdokai.pet.feature.categories.feed.PetCategoryViewModel.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject


@AndroidEntryPoint
class PetCategoryFeedFragment : Fragment(R.layout.fragment_category_feed) {

    private val viewModel by viewModels<PetCategoryViewModel>()
    private val binding by viewBinding(FragmentCategoryFeedBinding::bind)

    private var adapter: PetCategoryFeedAdapter =
        PetCategoryFeedAdapter { image -> viewModel.onFeedItemClicked(image) }

    @Inject
    lateinit var petDetailsNavigator: PetDetailsNavigator

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
        binding.categoriesRecyclerView.adapter = adapter
        val layoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        binding.categoriesRecyclerView.layoutManager = layoutManager
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
                    is Event.NavigateToPetDetails ->
                        petDetailsNavigator.open(
                        navController = findNavController(),
                        petID = event.petID,
                    )
                    is Event.ShowError -> requireContext().showDefaultErrorDialog(
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
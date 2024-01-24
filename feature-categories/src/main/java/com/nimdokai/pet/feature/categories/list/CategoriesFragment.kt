package com.nimdokai.pet.feature.categories.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nimdokai.core_util.navigation.PetCategoryFeedNavigator
import com.nimdokai.core_util.viewBinding
import com.nimdokai.pet.core.resources.views.showDefaultErrorDialog
import com.nimdokai.pet.feature.categories.R
import com.nimdokai.pet.feature.categories.databinding.FragmentCategoriesBinding
import com.nimdokai.pet.feature.categories.overview.OverviewViewModel
import com.nimdokai.pet.feature.categories.overview.PetCategoriesEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val viewModel by viewModels<OverviewViewModel>()
    private val binding by viewBinding(FragmentCategoriesBinding::bind)

    private var adapter: CategoriesAdapter =
        CategoriesAdapter { category -> viewModel.onCategoryClicked() }

    @Inject
    lateinit var categoryFeedNavigator: PetCategoryFeedNavigator

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
            viewModel.currentConditionsUiState.collect { state ->
            }
        }
    }

    private fun observeEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is PetCategoriesEvent.NavigateToCategoryFeed -> categoryFeedNavigator.open(
                        navController = findNavController(),
                        categoryID = event.categoryID
                    )

                    is PetCategoriesEvent.ShowError -> requireContext().showDefaultErrorDialog(
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
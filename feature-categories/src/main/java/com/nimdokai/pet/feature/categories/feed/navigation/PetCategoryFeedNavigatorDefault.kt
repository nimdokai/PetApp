package com.nimdokai.pet.feature.categories.feed.navigation

import androidx.navigation.NavController
import com.nimdokai.core_util.navigation.PetCategoryFeedNavigator
import com.nimdokai.core_util.navigation.PetCategoryFeedNavigator.PetCategoryFeedArgs
import com.nimdokai.core_util.navigation.open
import com.nimdokai.pet.core.util.R
import javax.inject.Inject


class PetCategoryFeedNavigatorDefault @Inject constructor() : PetCategoryFeedNavigator {

    override fun open(navController: NavController, categoryID: String) {
        navController.open(
            destination = R.id.category_feed_nav_graph,
            args = PetCategoryFeedArgs(categoryID)
        )
    }

}
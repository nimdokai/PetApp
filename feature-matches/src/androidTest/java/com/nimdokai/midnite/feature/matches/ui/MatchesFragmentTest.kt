package com.nimdokai.midnite.feature.matches.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.nimdokai.midnite.core.data.di.DataModuleBinder
import com.nimdokai.midnite.core.data.di.DataModuleProvider
import com.nimdokai.midnite.core.testing.HiltTestActivity
import com.nimdokai.midnite.data.FakeMatchesRepository
import com.nimdokai.midnite.feature.matches.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class MatchesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var matchesRepository: FakeMatchesRepository

    @Before
    fun init() {
        hiltRule.inject()
    }


    @Test
    fun displayMatches_whenRepositoryHasData() {

        // WHEN - On startup
        launchActivity()

        // THEN - Verify task is displayed on screen
        onView(withText("TeamA")).check(matches(isDisplayed()))
    }

    private fun launchActivity(): ActivityScenario<HiltTestActivity>? {
        val activityScenario = launch(HiltTestActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.matchesRecyclerView) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }

}
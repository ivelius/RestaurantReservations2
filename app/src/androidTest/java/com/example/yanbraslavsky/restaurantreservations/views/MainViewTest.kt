package com.example.yanbraslavsky.restaurantreservations.views

import android.content.Intent
import android.support.annotation.StringRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainView
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainViewTest {

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<MainView> =
            ActivityTestRule(MainView::class.java, true,
                    false)

    @Before
    fun setup() {
        //launch activity using empty intent (no arguments needed for now ...)
        mActivityTestRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
    }

    /**
     * Make sure all initial UI elements are placed on the screen , and visible
     * to the user.
     */
    @Test
    fun allUiElementsAreDisplayed_Test() {

        // Check that start button and the title are displayed
        onView(withId(R.id.startBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.startBtn)).check(matches(withText(getString(R.string.start))))

        //Make sure title is displayed
        onView(withText(getString(R.string.app_name))).check(matches(isDisplayed()))
    }


    @Test
    fun navigateToCustomersScreen_Test() {
        mActivityTestRule.activity.showCustomersScreen()
        //Make sure title of customers screen is displayed
        onView(withText(getString(R.string.customers_screen_title))).check(matches(isDisplayed()))
    }

    private fun getString(@StringRes resId: Int) = mActivityTestRule.activity.getString(resId)
}
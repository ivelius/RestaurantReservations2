package com.example.yanbraslavsky.restaurantreservations.views

import android.content.Intent
import android.support.annotation.StringRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yanbraslavsky.restaurantreservations.BaseActivityTest
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersContract
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersView
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class CustomersViewTest : BaseActivityTest() {

    private lateinit var mCustomersPresenter: CustomersContract.Presenter

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<CustomersView> =
            ActivityTestRule(CustomersView::class.java, true,
                    false)

    @Before
    override fun setup() {
        super.setup()

        //we use our mocked presenter to be injected into the view using dagger
        mCustomersPresenter = Mockito.mock(CustomersContract.Presenter::class.java)
        mTestAppModule.mMockedCustomerPresenter = mCustomersPresenter

        //launch activity using empty intent (no arguments needed for now ...)
        mActivityTestRule.launchActivity(Intent())

    }

    @After
    override fun tearDown() {
    }


    /**
     * Make sure all initial UI elements are placed on the screen , and visible
     * to the user.
     */
    @Test
    fun allUiElementsAreDisplayed_Test() {
        // Check all views are displayed
        onView(withText(R.string.customers_screen_title)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }


//    @Test
//    fun navigateToCustomersScreen_Test() {
//        mActivityTestRule.activity.showCustomersScreen()
//        //Make sure title of customers screen is displayed
//        onView(withText(getString(R.string.customers_screen_title))).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun presenterBind_Test() {
//        Mockito.verify(mCustomersPresenter).bind(mActivityTestRule.activity)
//    }
//
//    @Test
//    fun presenterStartButtonClick_Test() {
//        onView(withId(R.id.startBtn)).perform(click())
//        Mockito.verify(mCustomersPresenter).startButtonClicked()
//    }


}
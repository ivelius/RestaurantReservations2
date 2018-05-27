package com.example.yanbraslavsky.restaurantreservations.jorneys

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yanbraslavsky.restaurantreservations.BaseViewTest
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersAdapter
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainView
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationAdapter
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import com.example.yanbraslavsky.restaurantreservations.utils.EspressoCustomMarchers
import com.example.yanbraslavsky.restaurantreservations.utils.StubsProvider
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ReservationJourneyTest : BaseViewTest() {

    //For this test we use real implementation of everything except tables data
    private val mFakeTables = StubsProvider.createFakeTablesList(4)

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<MainView> =
            ActivityTestRule(MainView::class.java, true,
                    false)


    @Before
    override fun setup() {
        super.setup()
        //launch activity using empty intent (no arguments needed for now ...)
        mActivityTestRule.launchActivity(Intent())
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun reservationJourney_Test() {

        //waiter opens the app and clicks the start button
        onView(withId(R.id.startBtn)).perform(click())

        //Waiter clicks on the first customer in the list
        val indexOfSelectedCustomer = 1
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CustomersAdapter.ViewHolder>(indexOfSelectedCustomer, click()))

        //Waiter scrolls to first available table
        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollToPosition<ReservationAdapter.ViewHolder>(StubsProvider.INDEX_OF_AVAILABLE_NOT_SELECTED_TABLE))

        //Waiter clicks on available table
        onView(EspressoCustomMarchers.withRecyclerView(R.id.recyclerView)
                .atPosition(StubsProvider.INDEX_OF_AVAILABLE_NOT_SELECTED_TABLE))
                .perform(click())


        //Waiter makes sure the table he clicked is now selected
        //then he clicks the table again to deselect it
        onView(EspressoCustomMarchers.withRecyclerView(R.id.recyclerView).atPosition(StubsProvider.INDEX_OF_AVAILABLE_NOT_SELECTED_TABLE))
                .check(ViewAssertions.matches(hasDescendant(allOf(
                        withId(R.id.imageButton),
                        isEnabled(),
                        isSelected()
                ))))
    }

}
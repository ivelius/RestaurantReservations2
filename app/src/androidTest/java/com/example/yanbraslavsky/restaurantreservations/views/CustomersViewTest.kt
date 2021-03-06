package com.example.yanbraslavsky.restaurantreservations.views

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yanbraslavsky.restaurantreservations.BaseViewTest
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersAdapter
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersContract
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersView
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationView
import junit.framework.Assert.assertTrue
import kotlinx.android.synthetic.main.activity_table_selection.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
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
class CustomersViewTest : BaseViewTest() {

    private lateinit var mCustomersPresenter: CustomersContract.Presenter
    private val mFakeCustomers = createListOfFakeCustomers()

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
        super.tearDown()
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

    @Test
    fun loadingView_Test() {
        mActivityTestRule.activity.runOnUiThread({
            mActivityTestRule.activity.showLoading()
        })

        wait()

        onView(withId(R.id.loading_overlay)).check(matches(isDisplayed()))

        mActivityTestRule.activity.runOnUiThread({
            mActivityTestRule.activity.stopLoading()
        })

        wait()

        onView(withId(R.id.loading_overlay)).check(matches(not(isDisplayed())))
    }


    @Test
    fun showUserData_Test() {
        mActivityTestRule.activity.runOnUiThread({
            mActivityTestRule.activity.showCustomers(mFakeCustomers)
        })

        //it takes a moment for view to redraw itself
        //there are different ways to make this test , all are not pretty
        wait()

        val selectedCustomer = mFakeCustomers.last()

        //Special position using RecyclerViewActions
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<CustomersAdapter.ViewHolder>(mFakeCustomers.indexOf(selectedCustomer)))
        onView(allOf(withId(R.id.firstName), withText(selectedCustomer.customerFirstName),
                        isDisplayed())).check(matches(withText(selectedCustomer.customerFirstName)))

        val recyclerView = mActivityTestRule.activity.recyclerView
        assertTrue(recyclerView.adapter.itemCount == mFakeCustomers.size)

    }

    @Test
    fun customerClick_Test() {
        mActivityTestRule.activity.runOnUiThread({
            mActivityTestRule.activity.showCustomers(mFakeCustomers)
        })

        //it takes a moment for view to redraw itself
        //there are different ways to make this test , all are not pretty
        wait()

        val selectedCustomer = mFakeCustomers.last()
        val indexOfSelectedCustomer = mFakeCustomers.indexOf(selectedCustomer)

        //scroll to customer and click
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<CustomersAdapter.ViewHolder>(indexOfSelectedCustomer))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<CustomersAdapter.ViewHolder>(indexOfSelectedCustomer, click()))

        Mockito.verify(mCustomersPresenter).onItemClicked(selectedCustomer)

    }

    @Test
    fun presenterBind_Test() {
        Mockito.verify(mCustomersPresenter).bind(mActivityTestRule.activity)
    }

    @Test
    fun navigateToReservationsScreen_Test() {
        mActivityTestRule.activity.openReservationScreenForCustomer(mFakeCustomers.first())

        //make sure customers activity is launched
        intended(IntentMatchers.hasComponent(ReservationView::class.java.name))
    }

    private fun createListOfFakeCustomers(): List<CustomerEntity> {
        val fakeData = ArrayList<CustomerEntity>()
        for (i in 0..10) {
            fakeData.add(CustomerEntity(0, "fake$i", "$i", i))
        }
        return fakeData
    }
}
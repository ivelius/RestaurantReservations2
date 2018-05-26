package com.example.yanbraslavsky.restaurantreservations.views

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yanbraslavsky.restaurantreservations.BaseActivityTest
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationView
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.*


/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class ReservationViewTest : BaseActivityTest() {

    private lateinit var mReservationPresenter: ReservationContract.Presenter
    private val mFakeCustomer = CustomerEntity(0, "Uncle", "Bob", 1)
    private val mFakeTables = createFakeTablesList()

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<ReservationView> =
            ActivityTestRule(ReservationView::class.java, true,
                    false)

    @Before
    override fun setup() {
        super.setup()
        //we use our mocked presenter to be injected into the view using dagger
        mReservationPresenter = Mockito.mock(ReservationContract.Presenter::class.java)
        mTestAppModule.mMockedReservationsPresenter = mReservationPresenter

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
        onView(withText(R.string.reservation_screen_title)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun presenterBind_Test() {
        Mockito.verify(mReservationPresenter).bind(mActivityTestRule.activity)
    }


//    @Test
//    fun showUserData_Test() {
//        mActivityTestRule.activity.runOnUiThread({
//            mActivityTestRule.activity.showCustomers(mTableCustomers)
//        })
//
//        //it takes a moment for view to redraw itself
//        //there are different ways to make this test , all are not pretty
//        wait()
//
//        val selectedCustomer = mTableCustomers.last()
//
//        //Special position using RecyclerViewActions
//        onView(withId(R.id.recyclerView))
//                .perform(RecyclerViewActions.scrollToPosition<CustomersAdapter.ViewHolder>(mTableCustomers.indexOf(selectedCustomer)))
//        onView(allOf(withId(R.id.firstName), withText(selectedCustomer.customerFirstName),
//                        isDisplayed())).check(matches(withText(selectedCustomer.customerFirstName)))
//
//        val recyclerView = mActivityTestRule.activity.recyclerView
//        assertTrue(recyclerView.adapter.itemCount == mTableCustomers.size)
//
//    }
//
//    @Test
//    fun customerClick_Test() {
//        mActivityTestRule.activity.runOnUiThread({
//            mActivityTestRule.activity.showCustomers(mTableCustomers)
//        })
//
//        //it takes a moment for view to redraw itself
//        //there are different ways to make this test , all are not pretty
//        wait()
//
//        val selectedCustomer = mTableCustomers.last()
//        val indexOfSelectedCustomer = mTableCustomers.indexOf(selectedCustomer)
//
//        //scroll to customer and click
//        onView(withId(R.id.recyclerView))
//                .perform(RecyclerViewActions.scrollToPosition<CustomersAdapter.ViewHolder>(indexOfSelectedCustomer))
//        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<CustomersAdapter.ViewHolder>(indexOfSelectedCustomer, click()))
//
//        Mockito.verify(mReservationPresenter).onItemClicked(selectedCustomer)
//
//    }
//


    private fun createFakeTablesList(): List<TableEntity> {
        val fakeData = ArrayList<TableEntity>()

        //first table is available
        fakeData.add(TableEntity(0, true, 0))
        //second table is reserved by someone else
        fakeData.add(TableEntity(0, false, 1, 89))

        //rest of the tables are random
        val random = Random()
        for (i in 2..10) {
            fakeData.add(TableEntity(0, random.nextBoolean(), i, 5))
        }
        return fakeData
    }
}
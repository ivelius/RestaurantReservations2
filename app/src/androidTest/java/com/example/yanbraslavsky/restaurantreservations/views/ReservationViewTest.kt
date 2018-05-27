package com.example.yanbraslavsky.restaurantreservations.views

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yanbraslavsky.restaurantreservations.BaseViewTest
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationAdapter
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationView
import com.example.yanbraslavsky.restaurantreservations.utils.EspressoCustomMarchers.Companion.withRecyclerView
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
import java.util.*


/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class ReservationViewTest : BaseViewTest() {

    private lateinit var mReservationPresenter: ReservationContract.Presenter
    private val mFakeCustomer = CustomerEntity(0, "Uncle", "Bob", 1)
    private lateinit var mFakeTables: List<ReservationContract.GridCellTableModel>

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<ReservationView> =
            ActivityTestRule(ReservationView::class.java, true,
                    false)

    @Before
    override fun setup() {
        super.setup()

        //we manipulate the data in some tests , hence we reinitialize the tables
        //for each test
        mFakeTables = createFakeTablesList()

        //we use our mocked presenter to be injected into the view using dagger
        mReservationPresenter = Mockito.mock(ReservationContract.Presenter::class.java)
        mTestAppModule.mMockedReservationsPresenter = mReservationPresenter

        //launch activity using a fake customer in the intent
        val intent = Intent()
        intent.putExtra(ReservationView.CUSTOMER_BUNDLE_EXTRA_KEY, mFakeCustomer)
        mActivityTestRule.launchActivity(intent)
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
    fun presenterBind_Test() {
        Mockito.verify(mReservationPresenter).bind(mActivityTestRule.activity)
    }

    @Test
    fun presenterSetCustomer_Test() {
        Mockito.verify(mReservationPresenter).setCustomer(mFakeCustomer)
    }

    @Test
    fun changeTitle_Test() {
        val title = "fakeTitle"
        mActivityTestRule.activity.runOnUiThread({
            mActivityTestRule.activity.changeTitle(title)
        })
        wait()
        onView(withText(title)).check(matches(isDisplayed()))
    }


    /**
     * In this test we want to make sure that tables are displayed correctly
     */
    @Test
    fun showTables_Test() {
        populateWithTables()

        // Make sure total tables in the adapter equals to total data model tables
        val recyclerView = mActivityTestRule.activity.recyclerView
        assertTrue(recyclerView.adapter.itemCount == mFakeTables.size)

        // find unavailable table and make sure it is in disabled state
        // we put this table explicitly there
        val unavailableTable = mFakeTables[2]

        //Scroll to unavailable table
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                .scrollToPosition<ReservationAdapter.ViewHolder>(mFakeTables.indexOf(unavailableTable)))

        //Make sure the image there is in the disabled state
        onView(withRecyclerView(R.id.recyclerView).atPosition(mFakeTables.indexOf(unavailableTable)))
                .check(matches(hasDescendant(allOf(
                        withId(R.id.imageButton),
                        not(isEnabled())
                ))))


        // find available table and make sure it is enabled state
        // we put this table explicitly there
        val availableTable = mFakeTables.first()

        //Scroll to available table
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                .scrollToPosition<ReservationAdapter.ViewHolder>(mFakeTables.indexOf(availableTable)))

        //Make sure the image there is in the enabled state
        onView(withRecyclerView(R.id.recyclerView).atPosition(mFakeTables.indexOf(availableTable)))
                .check(matches(hasDescendant(allOf(
                        withId(R.id.imageButton),
                        isEnabled(),
                        not(isSelected())
                ))))

        // find selected table and make sure it is selected by highlighted image
        // we put this table explicitly there
        val selectedTable = mFakeTables[3]

        //Scroll to available table
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                .scrollToPosition<ReservationAdapter.ViewHolder>(mFakeTables.indexOf(selectedTable)))

        //Make sure the image there is in the enabled AND selected state
        onView(withRecyclerView(R.id.recyclerView).atPosition(mFakeTables.indexOf(selectedTable)))
                .check(matches(hasDescendant(allOf(
                        withId(R.id.imageButton),
                        isEnabled(),
                        isSelected()
                ))))
    }


    @Test
    fun tableClick_Test() {
        populateWithTables()

        val availableTable = mFakeTables.first()
        //Scroll to available table
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                .scrollToPosition<ReservationAdapter.ViewHolder>(mFakeTables.indexOf(availableTable)))

        //click on the table
        onView(withRecyclerView(R.id.recyclerView).atPosition(mFakeTables.indexOf(availableTable)))
                .perform(click())

        //make sure click is delegated to presenter
        Mockito.verify(mReservationPresenter).onTableItemClick(availableTable)

    }

    @Test
    fun updateTable_Test() {
        populateWithTables()

        // find available unselected table , make sure that it
        val table = mFakeTables.first()

        //we already have test that ensures that this table in
        //enabled and not selected state
        //now lets update it
        table.mSelected = true

        //call update method on the view
        mActivityTestRule.activity.runOnUiThread({
            mActivityTestRule.activity.updateTable(table)
        })

        //Scroll to the table
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                .scrollToPosition<ReservationAdapter.ViewHolder>(mFakeTables.indexOf(table)))

        //Make sure the image there is in the enabled AND selected state
        onView(withRecyclerView(R.id.recyclerView).atPosition(mFakeTables.indexOf(table)))
                .check(matches(hasDescendant(allOf(
                        withId(R.id.imageButton),
                        isEnabled(),
                        isSelected()
                ))))
    }

    private fun createFakeTablesList(): List<ReservationContract.GridCellTableModel> {
        val fakeData = ArrayList<ReservationContract.GridCellTableModel>()

        //first table is available and not selected
        fakeData.add(ReservationContract.GridCellTableModel(TableEntity(0, true, 0), false, false))

        //second table is reserved by someone else
        fakeData.add(ReservationContract.GridCellTableModel(
                TableEntity(0, true, 0, 98), false, true))

        //third table is unavailable
        fakeData.add(ReservationContract.GridCellTableModel(TableEntity(0, false, 0), false, false))


        //fourth table is reserved by current fake customer and hence is selected
        fakeData.add(ReservationContract.GridCellTableModel(TableEntity(0, true, 0, mFakeCustomer.customerId), true, false))

        //rest of the tables are random
        val random = Random()
        for (i in fakeData.size..10) {
            fakeData.add(ReservationContract.GridCellTableModel(TableEntity(0, random.nextBoolean(), 0), false, false))
        }
        return fakeData
    }

    private fun populateWithTables() {
        mActivityTestRule.activity.runOnUiThread({
            mActivityTestRule.activity.showTables(mFakeTables)
        })

        // it takes a moment for view to redraw itself
        // there are different ways to make this test , all are not pretty
        wait()
    }
}
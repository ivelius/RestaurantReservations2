package com.example.yanbraslavsky.restaurantreservations.views

import android.content.Intent
import android.support.annotation.StringRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainView
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
class MainViewTest {

    private lateinit var mMainPresenter: MainContract.Presenter

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<MainView> =
            ActivityTestRule(MainView::class.java, true,
                    false)

    @Before
    fun setup() {
        //launch activity using empty intent (no arguments needed for now ...)
        mActivityTestRule.launchActivity(Intent())
        mMainPresenter = Mockito.mock(MainContract.Presenter::class.java)
        //assign a mocked presenter instead of injected
        mActivityTestRule.activity.mPresenter = mMainPresenter
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
    fun startButtonClick_Test() {
        // Check that start button and the title are displayed
        onView(withId(R.id.startBtn)).perform(ViewActions.click())
        Mockito.verify(mMainPresenter).startButtonClicked()
    }

    @Test
    fun navigateToCustomersScreen_Test() {
        mActivityTestRule.activity.showCustomersScreen()
        //Make sure title of customers screen is displayed
        onView(withText(getString(R.string.customers_screen_title))).check(matches(isDisplayed()))
    }


    //    /**
//     * When user clicks search icon on action bar ,
//     * input text area should appear with an expected hint.
//     */
//    @Test
//    fun testSearchBarClick() {
//        //open search view
//        onView(isAssignableFrom(android.support.v7.widget.SearchView::class.java)).perform(click())
//        //make sure hint is visible and contains the correct text
//        onView(isAssignableFrom(SearchView.SearchAutoComplete::class.java)).check(matches(allOf(
//                withHint(mActivityTestRule.activity.getString(R.string.search_hint)), isDisplayed())))
//    }
//
//    /**
//     * When user inputs nothing into the search area and tries to search
//     * sanckbar with error should appear.
//     */
//    @Test
//    fun testEmptySearch() {
//        //open search
//        onView(isAssignableFrom(android.support.v7.widget.SearchView::class.java))
//                .perform(click())
//
//        //reset the text
//        onView(isAssignableFrom(AutoCompleteTextView::class.java))
//                .perform(typeText("asd"), clearText())
//
//        //click the search button
//        onView(withId(R.id.fab_btn)).perform(click())
//
//        //make sure title is changed accordingly
//        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle("")))
//
//        //make sure error message displayed in a snack bar
//        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(containsString("404"))))
//                .check(matches(isDisplayed()))
//    }
//
//    /**
//     * After user inputs valid username  and presses search, list view with
//     * a predefined structure should appear.
//     */
//    @Test
//    fun testValidSearch() {
//        searchUser("ivelius")
//
//        //make sure title is changed accordingly
//        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle("ivelius")))
//
//        //make sure relevant data appear
//        onView(withId(R.id.recycler_view))
//                .perform(RecyclerViewActions.actionOnItemAtPosition<ReposAdapter.ViewHolder>(0,
//                        scrollTo()))
//
//        //as for now , we know that the first item in the list has a specific title
//        //it is not a future proof solution . Alternately we could use mocked data
//        onView(withRecyclerView(R.id.recycler_view)
//                .atPositionOnView(0, R.id.repo_name_text_view))
//                .check(matches(withText("AsanaRebelTask")))
//    }
//
//
//    /**
//     * When user taps a list item , activity should navigate to detailed Activity
//     */
//    @Test
//    fun testItemClick() {
//        searchUser("ivelius")
//
//        //click on the first item appearing in the collection
//        onView(withRecyclerView(R.id.recycler_view)
//                .atPosition(0)).perform(click())
//
//        //make sure details activity is launched
//        intended(hasComponent(DetailsActivity::class.java.name))
//    }
//
//    private fun searchUser(username: String) {
//        //open search view
//        onView(isAssignableFrom(SearchView::class.java))
//                .perform(click())
//
//        //type in username and press enter on keyboard
//        onView(isAssignableFrom(AutoCompleteTextView::class.java))
//                .perform(typeText(username), pressKey(KeyEvent.KEYCODE_ENTER))
//    }
    private fun getString(@StringRes resId: Int) = mActivityTestRule.activity.getString(resId)
}
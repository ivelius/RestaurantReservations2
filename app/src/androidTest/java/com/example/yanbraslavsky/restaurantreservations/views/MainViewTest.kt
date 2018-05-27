package com.example.yanbraslavsky.restaurantreservations.views

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yanbraslavsky.restaurantreservations.BaseViewTest
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersView
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
class MainViewTest : BaseViewTest() {

    private lateinit var mMainPresenter: MainContract.Presenter

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<MainView> =
            ActivityTestRule(MainView::class.java, true,
                    false)

    @Before
    override fun setup() {
        super.setup()

        //we use our mocked presenter to be injected into the view
        //using dagger
        mMainPresenter = Mockito.mock(MainContract.Presenter::class.java)
        mTestAppModule.mMockedMainPresenter = mMainPresenter

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

        // Check that start button and the title are displayed
        onView(withId(R.id.startBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.startBtn)).check(matches(withText(getString(R.string.start))))

        //Make sure title is displayed
        onView(withText(getString(R.string.app_name))).check(matches(isDisplayed()))
    }


    @Test
    fun navigateToCustomersScreen_Test() {
        mActivityTestRule.activity.showCustomersScreen()
        //make sure customers activity is launched
        intended(hasComponent(CustomersView::class.java.name))
    }

    @Test
    fun presenterBind_Test() {
        Mockito.verify(mMainPresenter).bind(mActivityTestRule.activity)
    }

    @Test
    fun presenterStartButtonClick_Test() {
        onView(withId(R.id.startBtn)).perform(click())
        Mockito.verify(mMainPresenter).startButtonClicked()
    }
}
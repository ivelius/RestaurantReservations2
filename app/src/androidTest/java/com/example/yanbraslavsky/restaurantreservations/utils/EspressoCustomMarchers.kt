package com.example.yanbraslavsky.restaurantreservations.utils

import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.Toolbar
import android.view.View
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import android.support.test.espresso.util.HumanReadables
import android.support.test.espresso.PerformException
import android.support.test.espresso.util.TreeIterables
import android.support.test.espresso.UiController
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.asanarebel.yanbraslavski.asanarebeltask.utils.RecyclerViewMatcher
import java.util.concurrent.TimeoutException


/**
 * Created by yan.braslavski on 11/15/17.
 */
class EspressoCustomMarchers {
    companion object {
        fun withToolbarTitle(title: CharSequence): Matcher<Any> {
            return withToolbarTitle(`is`(title))
        }

        private fun withToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<Any> {
            return object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {
                public override fun matchesSafely(toolbar: Toolbar): Boolean {
                    return textMatcher.matches(toolbar.title)
                }

                override fun describeTo(description: Description) {
                    description.appendText("with toolbar title: ")
                    textMatcher.describeTo(description)
                }
            }
        }

        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }
    }

    fun withResourceName(resourceName: String): Matcher<View> {
        return withResourceName(`is`(resourceName))
    }

    fun withResourceName(resourceNameMatcher: Matcher<String>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with resource name: ")
                resourceNameMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val id = view.id
                return (id != View.NO_ID && id != 0 && view.resources != null
                        && resourceNameMatcher.matches(view.resources.getResourceName(id)))
            }
        }
    }

    /** Perform action of waiting for a specific view id.  */
    fun waitForView(viewId: Int, millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id <$viewId> during $millis millis."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher = withId(viewId)

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)

                // timeout happens
                throw PerformException.Builder()
                        .withActionDescription(this.description)
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(TimeoutException())
                        .build()
            }
        }
    }
}
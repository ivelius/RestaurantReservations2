package com.asanarebel.yanbraslavski.asanarebeltask.utils

import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.Toolbar
import android.view.View
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


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
}
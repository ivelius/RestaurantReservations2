package com.example.yanbraslavsky.restaurantreservations

import android.support.annotation.StringRes
import android.support.test.espresso.intent.Intents
import com.example.yanbraslavsky.restaurantreservations.di.DaggerAppComponent
import com.example.yanbraslavsky.restaurantreservations.di.app.TestAppModule

/**
 * Created by yan.braslavski on 11/15/17.
 */
abstract class BaseActivityTest {

    protected val mTestAppModule = TestAppModule(App.instance)

    open fun setup() {

        //Intents framework needed to test navigation between activities
        Intents.init()

        //Replace the app component modules with our test modules
        App.appComponent = DaggerAppComponent
                .builder()
                .appModule(mTestAppModule)
                .build()
    }

    open fun tearDown() {

    }

    internal fun getString(@StringRes resId: Int) = App.instance.getString(resId)
}
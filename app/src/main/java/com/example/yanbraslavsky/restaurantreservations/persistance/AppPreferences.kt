package com.example.yanbraslavsky.restaurantreservations.persistance

import android.content.SharedPreferences

class AppPreferences(val mSharedPreferences: SharedPreferences) {

    private val KEY_FIRST_LAUNCH:String = "key_first_launch"

    fun isFirstLaunch(): Boolean {
        val firstLaunch = mSharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
        if (firstLaunch) {
            mSharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
        }
        return firstLaunch
    }
}
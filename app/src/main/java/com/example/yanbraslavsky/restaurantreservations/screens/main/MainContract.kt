package com.example.yanbraslavsky.restaurantreservations.screens.main

import com.example.yanbraslavsky.restaurantreservations.mvp.BaseContract


object MainContract {
    /**
     * Here we define the communication :
     * Presenter -> View
     */
    interface View : BaseContract.View {
        fun showCustomersScreen()
    }

    /**
     * Here we define the communication :
     * View -> Presenter
     */
    interface Presenter : BaseContract.Presenter<View> {
        fun startButtonClicked()
    }
}
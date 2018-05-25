package com.example.yanbraslavsky.restaurantreservations.screens.main

import com.example.yanbraslavsky.restaurantreservations.mvp.BasePresenter
import javax.inject.Inject

open class MainPresenter @Inject constructor()
    : BasePresenter<MainContract.View>(), MainContract.Presenter {


    override fun startButtonClicked() {
        mBoundView?.showCustomersScreen()
    }

}
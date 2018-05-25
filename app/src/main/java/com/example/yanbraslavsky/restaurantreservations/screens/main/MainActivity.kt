package com.example.yanbraslavsky.restaurantreservations.screens.main

import android.os.Bundle
import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.activities.CustomerSelectionActivity
import com.example.yanbraslavsky.restaurantreservations.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var mPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        mPresenter.bind(this)
        startBtn.setOnClickListener({
            mPresenter.startButtonClicked()
        })
    }

    override fun showCustomersScreen() {
        CustomerSelectionActivity.open(this)
    }
}
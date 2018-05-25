package com.example.yanbraslavsky.restaurantreservations.screens.customers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.mvp.BaseView
import javax.inject.Inject

class CustomersView : BaseView(), CustomersContract.View {

    @Inject
    lateinit var mPresenter: CustomersContract.Presenter

    companion object {
        fun open(fromContext: Context) {
            fromContext.startActivity(Intent(fromContext, CustomersView::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_selection)

        App.appComponent.inject(this)
        mPresenter.bind(this)
    }

    override fun showCustomers(data: List<CustomerModel>) {
        showError("shit goes here")
    }
}

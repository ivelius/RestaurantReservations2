package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.mvp.BaseView
import javax.inject.Inject

class ReservationView : BaseView(), ReservationContract.View {

    @Inject
    lateinit var mPresenter: ReservationContract.Presenter

    companion object {

        private const val CUSTOMER_BUNDLE_EXTRA_KEY = "customer_bundle_extra_key"

        fun open(fromContext: Context, withCustomerModel: CustomerModel) {
            val intent = Intent(fromContext, ReservationView::class.java)
            intent.putExtra(CUSTOMER_BUNDLE_EXTRA_KEY, withCustomerModel)
            fromContext.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_selection)

        App.appComponent.inject(this)
        mPresenter.bind(this)
    }


    override fun showTables(data: List<Boolean>) {
        // TODO : shit goes here  ...
    }
}
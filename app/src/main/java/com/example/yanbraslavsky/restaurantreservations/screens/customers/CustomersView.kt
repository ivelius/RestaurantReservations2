package com.example.yanbraslavsky.restaurantreservations.screens.customers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationView
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.mvp.BaseView
import kotlinx.android.synthetic.main.activity_customer_selection.*
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
        initRecyclerView()
        App.appComponent.inject(this)
        mPresenter.bind(this)
    }

    private fun initRecyclerView() {
        recyclerView?.let {
            val linearLayoutManager = LinearLayoutManager(it.context)
            it.layoutManager = linearLayoutManager
            val dividerItemDecoration = DividerItemDecoration(it.context,
                    linearLayoutManager.orientation)
            it.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun showCustomers(data: List<CustomerEntity>) {
        recyclerView?.adapter = CustomersAdapter(data, {
            mPresenter.onItemClicked(it)
        })
    }

    override fun openReservationScreenForCustomer(customer: CustomerEntity) {
        ReservationView.open(this, customer)
    }
}
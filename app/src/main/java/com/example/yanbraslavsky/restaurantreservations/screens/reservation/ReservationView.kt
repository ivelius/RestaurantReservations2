package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import com.example.yanbraslavsky.restaurantreservations.mvp.BaseView
import kotlinx.android.synthetic.main.activity_customer_selection.*
import javax.inject.Inject

class ReservationView : BaseView(), ReservationContract.View {

    @Inject
    lateinit var mPresenter: ReservationContract.Presenter

    companion object {

        const val CUSTOMER_BUNDLE_EXTRA_KEY = "customer_bundle_extra_key"

        fun open(fromContext: Context, withCustomer: CustomerEntity) {
            val intent = Intent(fromContext, ReservationView::class.java)
            intent.putExtra(CUSTOMER_BUNDLE_EXTRA_KEY, withCustomer)
            fromContext.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_selection)
        initRecyclerView()

        val customer = intent.getParcelableExtra<CustomerEntity>(CUSTOMER_BUNDLE_EXTRA_KEY)

        App.appComponent.inject(this)
        customer?.let {
            mPresenter.setCustomer(it)
        }

        mPresenter.bind(this)
    }

    private fun initRecyclerView() {
        //TODO : Span count to be calculated depending on the view size
        //TODO : Ideally the grid cell item should be also dynamically calculated
        recyclerView?.let {
            val gridLayoutManager = GridLayoutManager(it.context, 8)
            it.layoutManager = gridLayoutManager
        }
    }


    override fun showTables(data: List<ReservationContract.GridCellTableModel>) {
        recyclerView?.adapter = ReservationAdapter(data, {
            mPresenter.onTableItemClick(it)
        })
    }

    override fun updateTable(tableItem: ReservationContract.GridCellTableModel) {
        val adapter = recyclerView?.adapter as? ReservationAdapter
        adapter?.updateTable(tableItem)
    }

    override fun onDestroy() {
        mPresenter.unbind()
        super.onDestroy()
    }
}
package com.example.yanbraslavsky.restaurantreservations.screens.customers

import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.mvp.BasePresenter
import javax.inject.Inject


open class CustomersPresenter @Inject constructor(private val mCustomersUseCase: CustomersUseCase)
    : BasePresenter<CustomersContract.View>(), CustomersContract.Presenter {

    private var mData: List<CustomerEntity>? = null

    override fun bind(view: CustomersContract.View) {
        super.bind(view)

        //if data already exists we show it without fetching
        mData?.let {
            showData(it)
            return
        }

        fetchData()
    }


    private fun showData(data: List<CustomerEntity>) {
        mBoundView?.showCustomers(data)
    }

    private fun fetchData() {
        mBoundView?.showLoading()
        mDisposablesBag.add(
                mCustomersUseCase.getCustomers()
                        .doFinally({ mBoundView?.stopLoading() })
                        .subscribe(
                                { result ->
                                    mBoundView?.let {
                                        mData = result
                                        showData(result)
                                    }
                                },
                                { error ->
                                    mBoundView?.let {
                                        it.showError(wrapErrorMessage(error))
                                    }
                                }
                        )
        )
    }

    override fun onItemClicked(customer: CustomerEntity) {
        mBoundView?.openReservationScreenForCustomer(customer)
    }


    private fun wrapErrorMessage(error: Throwable) = error.message ?: "Something is wrong :("
}
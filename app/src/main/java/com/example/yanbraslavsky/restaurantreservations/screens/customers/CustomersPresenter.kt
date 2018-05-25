package com.example.yanbraslavsky.restaurantreservations.screens.customers

import com.example.yanbraslavsky.restaurantreservations.api.RestarauntService
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.mvp.BasePresenter
import com.example.yanbraslavsky.restaurantreservations.usecases.CustomersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


open class CustomersPresenter @Inject constructor(private val mCustomersUseCase: CustomersUseCase)
    : BasePresenter<CustomersContract.View>(), CustomersContract.Presenter {

    private var mData: List<CustomerModel>? = null

    override fun bind(view: CustomersContract.View) {
        super.bind(view)

        //if data already exists we show it without fetching
        mData?.let {
            showData(it)
            return
        }

        fetchData()
    }


    private fun showData(data: List<CustomerModel>) {
        mBoundView?.showCustomers(data)
    }

    private fun fetchData() {
        mBoundView?.showLoading()
        mDisposablesBag.add(
                mCustomersUseCase.getRemoteCustomersList()
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

    private fun wrapErrorMessage(error: Throwable) = error.message ?: "Something is wrong :("
}
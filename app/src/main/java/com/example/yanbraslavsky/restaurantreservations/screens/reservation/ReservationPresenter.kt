package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import com.example.yanbraslavsky.restaurantreservations.mvp.BasePresenter
import com.example.yanbraslavsky.restaurantreservations.usecases.ReservationUseCase
import javax.inject.Inject


open class ReservationPresenter @Inject constructor(private val mReservationUseCase: ReservationUseCase)
    : BasePresenter<ReservationContract.View>(), ReservationContract.Presenter {

    private var mData: List<Boolean>? = null

    override fun bind(view: ReservationContract.View) {
        super.bind(view)

        //if data already exists we show it without fetching
        mData?.let {
            showData(it)
            return
        }

        fetchData()
    }


    private fun showData(data: List<Boolean>) {
        mBoundView?.showTables(data)
    }

    private fun fetchData() {
        mBoundView?.showLoading()
        mDisposablesBag.add(
                mReservationUseCase.getRemoteTablesList()
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
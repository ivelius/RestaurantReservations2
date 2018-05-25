package com.example.yanbraslavsky.restaurantreservations.mvp

import io.reactivex.disposables.CompositeDisposable


abstract class BasePresenter<T : BaseContract.View> : BaseContract.Presenter<T> {

    protected val mDisposablesBag: CompositeDisposable = CompositeDisposable()
    protected var mBoundView: T? = null

    override fun bind(view: T) {
        mBoundView = view
    }

    override fun unbind() {
        mBoundView = null
        mDisposablesBag.clear()
    }


}
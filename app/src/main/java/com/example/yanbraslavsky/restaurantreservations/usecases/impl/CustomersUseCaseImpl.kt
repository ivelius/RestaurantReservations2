package com.example.yanbraslavsky.restaurantreservations.usecases.impl

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestarauntService
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.usecases.CustomersUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CustomersUseCaseImpl @Inject constructor(private val mApiService: RestarauntService) : CustomersUseCase {

    init {
        App.appComponent.inject(this)
    }


    override fun getRemoteCustomersList(): Observable<List<CustomerModel>> {
        return mApiService.getCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


}
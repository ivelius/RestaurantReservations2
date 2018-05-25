package com.example.yanbraslavsky.restaurantreservations.screens.customers

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.usecases.CustomersUseCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CustomersUseCaseImpl @Inject constructor(private val mApiService: RestaurantService) : CustomersUseCase {

    init {
        App.appComponent.inject(this)
    }


    override fun getRemoteCustomersList(): Single<List<CustomerModel>> {
        return mApiService.getCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


}
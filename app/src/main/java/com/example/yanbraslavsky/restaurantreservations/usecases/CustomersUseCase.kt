package com.example.yanbraslavsky.restaurantreservations.usecases

import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import io.reactivex.Single


interface CustomersUseCase {

    fun getRemoteCustomersList(): Single<List<CustomerModel>>
}
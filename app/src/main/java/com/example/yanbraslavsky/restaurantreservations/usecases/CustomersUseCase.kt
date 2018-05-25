package com.example.yanbraslavsky.restaurantreservations.usecases

import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import io.reactivex.Observable


interface CustomersUseCase {

    fun getRemoteCustomersList(): Observable<List<CustomerModel>>
}
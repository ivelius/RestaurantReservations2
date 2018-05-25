package com.example.yanbraslavsky.restaurantreservations.screens.customers

import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import io.reactivex.Single


interface CustomersUseCase {

    fun getCustomers(): Single<List<CustomerEntity>>
}
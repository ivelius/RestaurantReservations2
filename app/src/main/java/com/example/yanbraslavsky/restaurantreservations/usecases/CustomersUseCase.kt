package com.example.yanbraslavsky.restaurantreservations.usecases

import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import io.reactivex.Single


interface CustomersUseCase {

    fun getCustomers(): Single<List<CustomerEntity>>
}
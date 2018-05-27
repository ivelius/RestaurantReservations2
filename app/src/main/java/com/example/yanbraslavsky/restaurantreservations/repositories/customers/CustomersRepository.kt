package com.example.yanbraslavsky.restaurantreservations.repositories.customers

import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import io.reactivex.Single


interface CustomersRepository {

    fun getCustomers(): Single<List<CustomerEntity>>
}
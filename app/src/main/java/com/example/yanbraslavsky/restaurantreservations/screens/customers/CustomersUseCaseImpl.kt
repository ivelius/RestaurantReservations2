package com.example.yanbraslavsky.restaurantreservations.screens.customers

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CustomersUseCaseImpl
@Inject constructor(private val mApiService: RestaurantService,
                    private val mRestaurantDatabase: RestaurantDatabase) : CustomersUseCase {

    init {
        App.appComponent.inject(this)
    }

    override fun getCustomers(): Single<List<CustomerEntity>> {
        //Try loading the data from local data base
        return mRestaurantDatabase.customerDao().getCustomers()
                .flatMap {
                    if (!it.isEmpty())
                        return@flatMap Single.just(it) else {
                        //no data in database , load from server
                        return@flatMap mApiService.getCustomers()
                                .map {

                                    //when the data is loaded from server we store it
                                    //in the data base for future use
                                    it.map {
                                        val customer = CustomerEntity(0, it.customerFirstName,
                                                it.customerLastName, it.id)
                                        mRestaurantDatabase.customerDao().insert(customer)
                                        return@map customer
                                    }
                                }
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
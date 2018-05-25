package com.example.yanbraslavsky.restaurantreservations.screens.customers

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.database.RestarauntDatabase
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.usecases.CustomersUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CustomersUseCaseImpl
@Inject constructor(private val mApiService: RestaurantService,
                    private val mRestarauntDatabase: RestarauntDatabase) : CustomersUseCase {

    init {
        App.appComponent.inject(this)
    }


    override fun getCustomers(): Single<List<CustomerEntity>> {
        //Try loading the data from local data base
        return mRestarauntDatabase.customerDao().getCustomers()
                .flatMap {
                    if (!it.isEmpty())
                        return@flatMap Single.just(it) else {
                        //no data in database , load from server
                        return@flatMap mApiService.getCustomers()
                                .map {
                                    it.map {
                                        CustomerEntity(0, it.customerFirstName,
                                                it.customerLastName, it.id)
                                    }
                                }
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


}
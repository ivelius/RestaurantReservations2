package com.example.yanbraslavsky.restaurantreservations.repositories.customers

import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import javax.inject.Inject

class CustomersRepositoryImpl
@Inject constructor(private val mApiService: RestaurantService,
                    private val mRestaurantDatabase: RestaurantDatabase) : CustomersRepository {


    override fun getCustomers(): Single<List<CustomerEntity>> {
        //Try loading the data from local data base
        return databaseObservable()
                //if data from local data base cannot be loaded , try loading from api
                .onErrorResumeNext(apiObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun databaseObservable(): Single<List<CustomerEntity>> {
        return Single.create {
            val list = mRestaurantDatabase.customerDao().getCustomers().blockingGet()
            if (list.isEmpty()) {
                it.onError(Error("Empty List"))
            } else {
                it.onSuccess(list)
            }
        }
    }

    private fun apiObservable(): Single<List<CustomerEntity>> {
        return Single.create {
            val list = mApiService.getCustomers().blockingGet()
            if (list == null || list.isEmpty()) {
                it.onError(Error("Error"))
            } else {

                list.forEach({
                    val customer = CustomerEntity(0, it.customerFirstName,
                            it.customerLastName, it.id)
                    mRestaurantDatabase.customerDao().insert(customer)
                })

                it.onSuccess(mRestaurantDatabase.customerDao().getCustomers().blockingGet())
            }
        }
    }
}
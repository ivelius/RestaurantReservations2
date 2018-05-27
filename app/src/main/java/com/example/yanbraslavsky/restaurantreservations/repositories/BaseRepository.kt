package com.example.yanbraslavsky.restaurantreservations.repositories

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseRepository<T> {

    internal fun getResource(): Single<T> {
        //Try loading the data from local data base
        return databaseObservable()
                //if data from local data base cannot be loaded , try loading from api
                .onErrorResumeNext(apiObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    abstract fun databaseObservable(): Single<T>
    abstract fun apiObservable(): Single<T>

//    private fun databaseObservable(): Single<T> {
//        return Single.create {
//            val list = mRestaurantDatabase.customerDao().getCustomers().blockingGet()
//            if (list.isEmpty()) {
//                it.onError(Error("Empty List"))
//            } else {
//                it.onSuccess(list)
//            }
//        }
//    }

//    private fun apiObservable(): Single<T> {
//        return Single.create {
//            val list = mApiService.getCustomers().blockingGet()
//            if (list == null || list.isEmpty()) {
//                it.onError(Error("Error"))
//            } else {
//
//                list.forEach({
//                    val customer = CustomerEntity(0, it.customerFirstName,
//                            it.customerLastName, it.id)
//                    mRestaurantDatabase.customerDao().insert(customer)
//                })
//
//                it.onSuccess(mRestaurantDatabase.customerDao().getCustomers().blockingGet())
//            }
//        }
//    }
}
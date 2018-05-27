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
}
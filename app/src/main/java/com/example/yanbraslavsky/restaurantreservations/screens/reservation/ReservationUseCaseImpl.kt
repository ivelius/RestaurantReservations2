package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.usecases.ReservationUseCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReservationUseCaseImpl @Inject constructor(private val mApiService: RestaurantService) : ReservationUseCase {

    init {
        App.appComponent.inject(this)
    }

    override fun getRemoteTablesList(): Single<List<Boolean>> {
        return mApiService.getTables()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
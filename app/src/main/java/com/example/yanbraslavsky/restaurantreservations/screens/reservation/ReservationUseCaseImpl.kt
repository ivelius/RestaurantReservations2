package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestarauntService
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.usecases.CustomersUseCase
import com.example.yanbraslavsky.restaurantreservations.usecases.ReservationUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReservationUseCaseImpl @Inject constructor(private val mApiService: RestarauntService) : ReservationUseCase {

    init {
        App.appComponent.inject(this)
    }

    override fun getRemoteTablesList(): Observable<List<Boolean>> {
        return mApiService.getTables()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
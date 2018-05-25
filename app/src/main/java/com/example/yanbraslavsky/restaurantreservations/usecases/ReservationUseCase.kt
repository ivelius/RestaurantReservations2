package com.example.yanbraslavsky.restaurantreservations.usecases

import io.reactivex.Single


interface ReservationUseCase {

    fun getRemoteTablesList(): Single<List<Boolean>>
}
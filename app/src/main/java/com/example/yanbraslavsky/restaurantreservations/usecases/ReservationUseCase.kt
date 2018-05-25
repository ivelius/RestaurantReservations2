package com.example.yanbraslavsky.restaurantreservations.usecases

import io.reactivex.Observable


interface ReservationUseCase {

    fun getRemoteTablesList(): Observable<List<Boolean>>
}